<script setup>
    // Todo las class y extras los aislare despues para limpiar codigo

import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const username = ref('')
const password = ref('')
const error = ref(false)
const isShaking = ref(false) // Controla la animación de error localoca xd

const handleLogin = () => {
  if (username.value === 'admin' && password.value === '123') {
    error.value = false
    router.push('/tickets')
  } else {
    error.value = true
    isShaking.value = true
    
    // quita la clase para que salfga de nuevo la animacion
    setTimeout(() => {
      isShaking.value = false
    }, 1000)
  }
}
</script>

<template>
  <div class="relative flex min-h-screen items-center justify-center overflow-hidden bg-gradient-to-br from-slate-900 via-indigo-950 to-purple-900 px-4">
    
    <div class="animate-pulse absolute top-10 left-10 h-72 w-72 rounded-full bg-purple-600/20 blur-3xl content-none"></div>
    <div class="animate-pulse absolute bottom-10 right-10 h-96 w-96 rounded-full bg-indigo-600/20 blur-3xl content-none" style="animation-delay: 2s;"></div>

    <div 
      class="animate__animated animate__jackInTheBox relative w-full max-w-md rounded-2xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl shadow-2xl"
      :class="{ 'animate__animated animate__shakeX border-red-500/50 bg-red-500/5': isShaking }"
    >
      
      <div class="text-center mb-8">
        <div class="inline-block animate__animated animate__heartBeat animate__infinite animate__slower text-5xl mb-3 filter drop-shadow-[0_0_15px_rgba(168,85,247,0.5)]">
          🆘
        </div>
        <h1 class="text-4xl font-extrabold tracking-wider text-white bg-clip-text bg-gradient-to-r from-purple-400 to-indigo-200">
          TICKERIA
        </h1>
        <p class="text-sm text-indigo-200/60 mt-1 uppercase tracking-widest font-semibold">
          Control Center
        </p>
      </div>

      <form @submit.prevent="handleLogin" class="space-y-6">
        
        <div class="group relative">
          <label class="block text-xs font-bold uppercase tracking-wider text-purple-300 mb-2 transition-colors group-focus-within:text-purple-400">
            Usuario
          </label>
          <input 
            v-model="username" 
            type="text" 
            placeholder="Ej: admin"
            class="w-full rounded-xl border border-white/10 bg-white/5 px-4 py-3 text-white placeholder-white/30 outline-none transition-all duration-300 focus:border-purple-500 focus:bg-white/10 focus:ring-4 focus:ring-purple-500/20"
            required
          >
        </div>

        <div class="group relative">
          <label class="block text-xs font-bold uppercase tracking-wider text-purple-300 mb-2 transition-colors group-focus-within:text-purple-400">
            Contraseña 
          </label>
          <input 
            v-model="password" 
            type="password" 
            placeholder="••••••••"
            class="w-full rounded-xl border border-white/10 bg-white/5 px-4 py-3 text-white placeholder-white/30 outline-none transition-all duration-300 focus:border-purple-500 focus:bg-white/10 focus:ring-4 focus:ring-purple-500/20"
            required
          >
        </div>

        <div v-if="error" class="animate__animated animate__headShake rounded-lg border border-red-500/30 bg-red-500/10 p-3 text-center text-sm font-semibold text-red-400 shadow-lg shadow-red-950/50">
          ⚠️ Acceso Denegado. Intenta de nuevo.
        </div>

        <button 
          type="submit" 
          class="w-full transform rounded-xl bg-gradient-to-r from-purple-600 to-indigo-600 py-3.5 text-sm font-bold uppercase tracking-widest text-white shadow-lg shadow-purple-900/40 transition-all duration-300 hover:scale-[1.03] hover:from-purple-500 hover:to-indigo-500 hover:shadow-purple-500/30 active:scale-[0.98]"
        >
          Iniciar Operaciones
        </button>
      </form>
      
      <div class="text-center mt-8">
        <a href="#" class="text-xs font-medium text-purple-300/50 transition-colors hover:text-purple-400 hover:underline">
          ¿Sistemas bloqueados? Recuperar llave
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Todo el peso visual se maneja ahora mediante las clases utilitarias de Tailwind */
</style>