/**
 * TECNOLOGÍA: Spring Web (REST Controller) / CORS
 * PROPÓSITO: Capa intermedia que expone las URLs endpoints (/api/tickets) para recibir peticiones HTTP.
 * Habilita @CrossOrigin para autorizar de manera segura la comunicación con el puerto del Frontend (Vue).
 */

package tickeria_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickeria_backend.model.Ticket;
import tickeria_backend.repository.TicketRepository;

import java.util.List;


@RestController
@RequestMapping("/api/tickets")  //Pal los datos json
@CrossOrigin(origins = "http://localhost:5173") // Pa que me acepte los datos del vue
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository; 

    // 1. Obtener el tablero completo (todas las columnas juntas)
    @GetMapping
    public List<Ticket> listarTickets() {
        return ticketRepository.findAllByOrderByOrdenAsc();
    }

    // 2. NUEVO: EndPoint para entrar a las pestañas individuales (Por hacer, En progreso, Completadas)
    // Ejemplo: http://localhost:8080/api/tickets/estado/POR_HACER
    // Separa cada lissta por estado, convierte en mayusculas para evitar errores.
    @GetMapping("/estado/{estado}")
    public List<Ticket> listarPorEstado(@PathVariable String estado) {
        return ticketRepository.findByEstadoOrderByOrdenAsc(estado.toUpperCase());
    }

    // 3. Crear un ticket nuevo
    // Ejecuta un comando INSERT INTO en MySQL 
    // Guarda el ticket nuevo. Devuelve el ticket recién creado (ahora con un ID asignado por la base de datos).

    @PostMapping
    public Ticket crearTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // 4. Actualizar estado y posición (Esencial para cuando arrastres una tarjeta de una pestaña o columna a otra)
    // Checa las actualizaciones por el ID  mete los nuevos estados  orden y prioridad a donde se movio y avienta un update
    // Esta tambien el else para evitar errores y para escalar mas el programa 
    @PutMapping("/{id}")
    public Ticket actualizarTicket(@PathVariable Long id, @RequestBody Ticket datosNuevos) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setEstado(datosNuevos.getEstado());
                    ticket.setOrden(datosNuevos.getOrden());
                    ticket.setPrioridad(datosNuevos.getPrioridad());
                    return ticketRepository.save(ticket);
                }).orElseThrow(() -> new RuntimeException("No se encontró el ticket con ID: " + id));
    }
}