<script setup>
// Todo las class y extras los aislare despues para limpiar codigo
// TECNOLOGÍAS: Vue 3 (Composition API) & Axios
// PROPÓSITO: Manejo del estado reactivo de las tres columnas del tablero Kanban.
// Utiliza Axios para realizar el consumo asíncrono de la API REST de Spring Boot (Carga, Creación y Actualización).
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const ticketsPorHacer = ref([])
const ticketsEnProgreso = ref([])
const ticketsCompletados = ref([])

// Variables para el formulario flotante recordatorio para cambiar a futuro
const mostrarModal = ref(false)
const formulario = ref({
  titulo: '',
  descripcion: '',
  cliente: '',
  prioridad: 'Media',
  estado: 'POR_HACER', // Por defecto entra a esta columna
  orden: 1
})

const cargarTickets = async () => {
  try {
    const respuesta = await axios.get('http://localhost:8080/api/tickets')
    const todosLosTickets = respuesta.data

    ticketsPorHacer.value = todosLosTickets.filter(t => t.estado === 'POR_HACER')
    ticketsEnProgreso.value = todosLosTickets.filter(t => t.estado === 'EN_PROGRESO')
    ticketsCompletados.value = todosLosTickets.filter(t => t.estado === 'COMPLETADO')
  } catch (error) {
    console.error('Error al conectar con el backend:', error)
  }
}

// NUEVO: Función para enviar el ticket a la base de datos
const guardarTicket = async () => {
  try {
    // Mandamos los datos del formulario al backend
    await axios.post('http://localhost:8080/api/tickets', formulario.value)
    
    // Ocultamos la ventana flotante
    mostrarModal.value = false
    
    // Limpiamos el formulario para la próxima vez
    formulario.value = { titulo: '', descripcion: '', cliente: '', prioridad: 'Media', estado: 'POR_HACER', orden: 1 }
    
    // Recargamos el tablero para que aparezca el nuevo ticket al instante
    cargarTickets()
  } catch (error) {
    console.error('Error al guardar el ticket:', error)
  }
}
// TECNOLOGÍA: HTML5 Drag and Drop API (Nativa)
// PROPÓSITO: Gestionar la transferencia de datos en memoria del navegador durante el movimiento físico de las tarjetas.
// 'iniciarArrastre' guarda el ID del ticket seleccionado; 'soltarTicket' cambia el estado y ejecuta el PUT en el sesrvidor.
// 1. Cuando agarras un ticket, guardamos su ID en la memoria temporal del navegador
const iniciarArrastre = (event, ticket) => {
  event.dataTransfer.setData('ticketId', ticket.id)
  event.dataTransfer.effectAllowed = 'move'
}

// 2. Cuando se suelta el ticket en una nueva columna
const soltarTicket = async (event, nuevoEstado) => {
  const ticketId = event.dataTransfer.getData('ticketId')
  
  // Buscamos el ticket exacto que se mueve juntando todas las listas
  const todosLosTickets = [...ticketsPorHacer.value, ...ticketsEnProgreso.value, ...ticketsCompletados.value]
  const ticketMovido = todosLosTickets.find(t => t.id == ticketId)

  // Al moverlo de columna se actualiza
  if (ticketMovido && ticketMovido.estado !== nuevoEstado) {
    // Actualizamos el estado localmente para que sea instantáneo
    ticketMovido.estado = nuevoEstado
    
    try {
      // Se avisa al backend de Java que el ticket cambió de columna
      await axios.put(`http://localhost:8080/api/tickets/${ticketId}`, ticketMovido)
      
      // Recarga las listas para asegurar que todo esté sincronizado con Docker
      cargarTickets()
    } catch (error) {
      console.error('Error al actualizar la posición del ticket:', error)
    }
  }
}
onMounted(() => {
  cargarTickets()
})

const handleLogout = () => {
  router.push('/')
}
</script>

<template>
  <div class="min-h-screen bg-slate-900 text-slate-100">
    
    <header class="flex items-center justify-between border-b border-slate-800 bg-slate-900/50 px-8 py-4 backdrop-blur-md">
      <div class="flex items-center space-x-3">
        <span class="text-3xl">🆘</span>
        <h1 class="text-2xl font-black tracking-wider text-transparent bg-clip-text bg-gradient-to-r from-purple-400 to-indigo-400">
          TICKERIA
        </h1>
      </div>
      <div class="flex items-center space-x-4">
        <span class="text-sm text-slate-400 font-medium">Operador: <strong class="text-purple-300">Admin</strong></span>
        <button 
          @click="handleLogout" 
          class="rounded-lg bg-slate-800 px-4 py-2 text-xs font-bold uppercase tracking-wider text-red-400 transition-colors hover:bg-red-500/10"
        >
          Cerrar Sesión
        </button>
      </div>
    </header>

    <main class="p-8">
      
      <div class="mb-8 flex items-center justify-between">
        <div>
          <h2 class="text-xl font-bold text-white">Tablero de Control</h2>
          <p class="text-sm text-slate-400">Gestiona y supervisa el estado de los reportes activos</p>
        </div>
        <button @click="mostrarModal = true"
          class="transform rounded-xl bg-gradient-to-r from-purple-600 to-indigo-600 px-5 py-3 text-sm font-bold uppercase tracking-wider text-white shadow-lg shadow-purple-900/20 transition-all hover:scale-105 hover:from-purple-500 hover:to-indigo-500"

                    >
          + Nuevo Ticket        
        </button>
      </div>

      <div class="grid grid-cols-1 gap-6 md:grid-cols-3">
        
        <div 
          @dragover.prevent 
          @drop="soltarTicket($event, 'POR_HACER')"
          class="rounded-2xl border border-slate-800 bg-slate-950/40 p-4 backdrop-blur-sm transition-colors hover:bg-slate-900/50"
        >
          <div class="mb-4 flex items-center justify-between px-2">
            <span class="text-sm font-bold uppercase tracking-widest text-slate-400">⏳ Por Hacer</span>
            <span class="rounded-full bg-slate-800 px-2.5 py-0.5 text-xs font-bold text-slate-300">{{ ticketsPorHacer.length }}</span>
          </div>
          
          <div class="space-y-3">
            <div 
              v-for="ticket in ticketsPorHacer" 
              :key="ticket.id"
              draggable="true" 
              @dragstart="iniciarArrastre($event, ticket)"
              class="animate__animated animate__fadeInUp group relative rounded-xl border border-slate-800 bg-slate-900 p-4 shadow-md transition-all hover:border-purple-500/50 hover:bg-slate-850 cursor-grab active:cursor-grabbing"
            >
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-semibold text-slate-500">#{{ ticket.id }}</span>
                <span 
                  class="rounded px-2 py-0.5 text-[10px] font-extrabold uppercase tracking-wide"
                  :class="ticket.prioridad === 'Alta' ? 'bg-red-500/10 text-red-400' : 'bg-yellow-500/10 text-yellow-400'"
                >
                  {{ ticket.prioridad }}
                </span>
              </div>
              <h3 class="font-bold text-slate-200 group-hover:text-purple-300 transition-colors">{{ ticket.titulo }}</h3>
              <p class="mt-2 text-xs text-slate-400">Cliente: <span class="text-slate-300 font-medium">{{ ticket.cliente }}</span></p>
            </div>
          </div>
        </div>

        <div 
          @dragover.prevent 
          @drop="soltarTicket($event, 'EN_PROGRESO')"
          class="rounded-2xl border border-slate-800 bg-slate-950/40 p-4 backdrop-blur-sm transition-colors hover:bg-slate-900/50"
        >
          <div class="mb-4 flex items-center justify-between px-2">
            <span class="text-sm font-bold uppercase tracking-widest text-indigo-400">⚡ En Progreso</span>
            <span class="rounded-full bg-indigo-950 text-indigo-300 px-2.5 py-0.5 text-xs font-bold">{{ ticketsEnProgreso.length }}</span>
          </div>

          <div class="space-y-3">
            <div 
              v-for="ticket in ticketsEnProgreso" 
              :key="ticket.id"
              draggable="true" 
              @dragstart="iniciarArrastre($event, ticket)"
              class="animate__animated animate__fadeInUp rounded-xl border border-indigo-900/30 bg-slate-900 p-4 shadow-md transition-all hover:border-indigo-500/50 cursor-grab active:cursor-grabbing"
            >
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-semibold text-slate-500">#{{ ticket.id }}</span>
                <span class="rounded bg-red-500/10 text-red-400 px-2 py-0.5 text-[10px] font-extrabold uppercase tracking-wide">{{ ticket.prioridad }}</span>
              </div>
              <h3 class="font-bold text-slate-200">{{ ticket.titulo }}</h3>
              <p class="mt-2 text-xs text-slate-400">Cliente: <span class="text-slate-300 font-medium">{{ ticket.cliente }}</span></p>
            </div>
          </div>
        </div>

        <div 
          @dragover.prevent 
          @drop="soltarTicket($event, 'COMPLETADO')"
          class="rounded-2xl border border-slate-800 bg-slate-950/40 p-4 backdrop-blur-sm transition-colors hover:bg-slate-900/50"
        >
          <div class="mb-4 flex items-center justify-between px-2">
            <span class="text-sm font-bold uppercase tracking-widest text-emerald-400">✅ Completados</span>
            <span class="rounded-full bg-emerald-950 text-emerald-300 px-2.5 py-0.5 text-xs font-bold">{{ ticketsCompletados.length }}</span>
          </div>

          <div class="space-y-3">
            <div 
              v-for="ticket in ticketsCompletados" 
              :key="ticket.id"
              draggable="true" 
              @dragstart="iniciarArrastre($event, ticket)"
              class="animate__animated animate__fadeInUp rounded-xl border border-slate-800 bg-slate-900 p-4 shadow-md opacity-60 line-through transition-all hover:opacity-100 hover:border-emerald-500/50 cursor-grab active:cursor-grabbing"
            >
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-semibold text-slate-500">#{{ ticket.id }}</span>
                <span class="rounded bg-slate-800 text-slate-400 px-2 py-0.5 text-[10px] font-extrabold uppercase tracking-wide">{{ ticket.prioridad }}</span>
              </div>
              <h3 class="font-bold text-slate-300">{{ ticket.titulo }}</h3>
              <p class="mt-2 text-xs text-slate-500">Cliente: <span class="text-slate-400 font-medium">{{ ticket.cliente }}</span></p>
            </div>
          </div>
        </div>

      </div>
    </main>
  </div>
  <div v-if="mostrarModal" class="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm transition-opacity">
      <div class="animate__animated animate__zoomIn w-full max-w-md rounded-2xl border border-slate-700 bg-slate-900 p-6 shadow-2xl">
        
        <h2 class="mb-5 text-xl font-black tracking-wide text-transparent bg-clip-text bg-gradient-to-r from-purple-400 to-indigo-400">
          CREAR NUEVO TICKET
        </h2>

        <div class="space-y-4">
          <div>
            <label class="mb-1 block text-xs font-bold text-slate-400 uppercase tracking-wider">Título del Reporte</label>
            <input v-model="formulario.titulo" type="text" placeholder="Ej: Falla en servidor..." class="w-full rounded-lg border border-slate-700 bg-slate-950 p-3 text-slate-200 focus:border-purple-500 focus:outline-none focus:ring-1 focus:ring-purple-500" />
          </div>

          <div>
            <label class="mb-1 block text-xs font-bold text-slate-400 uppercase tracking-wider">Cliente / Área</label>
            <input v-model="formulario.cliente" type="text" placeholder="Ej: Recursos Humanos" class="w-full rounded-lg border border-slate-700 bg-slate-950 p-3 text-slate-200 focus:border-purple-500 focus:outline-none focus:ring-1 focus:ring-purple-500" />
          </div>

          <div>
            <label class="mb-1 block text-xs font-bold text-slate-400 uppercase tracking-wider">Prioridad</label>
            <select v-model="formulario.prioridad" class="w-full rounded-lg border border-slate-700 bg-slate-950 p-3 text-slate-200 focus:border-purple-500 focus:outline-none focus:ring-1 focus:ring-purple-500">
              <option value="Baja">Baja (Verde)</option>
              <option value="Media">Media (Amarillo)</option>
              <option value="Alta">Alta (Rojo)</option>
            </select>
          </div>

          <div>
            <label class="mb-1 block text-xs font-bold text-slate-400 uppercase tracking-wider">Descripción detallada</label>
            <textarea v-model="formulario.descripcion" rows="3" placeholder="Describe el problema..." class="w-full rounded-lg border border-slate-700 bg-slate-950 p-3 text-slate-200 focus:border-purple-500 focus:outline-none focus:ring-1 focus:ring-purple-500"></textarea>
          </div>
        </div>

        <div class="mt-6 flex justify-end space-x-3">
          <button @click="mostrarModal = false" class="rounded-lg px-4 py-2 text-sm font-bold text-slate-400 transition-colors hover:bg-slate-800 hover:text-white">
            Cancelar
          </button>
          <button @click="guardarTicket" class="rounded-lg bg-gradient-to-r from-purple-600 to-indigo-600 px-6 py-2 text-sm font-bold text-white transition-all hover:scale-105 hover:from-purple-500 hover:to-indigo-500 shadow-lg shadow-purple-900/30">
            Guardar Ticket
          </button>
        </div>

      </div>
    </div>
</template>