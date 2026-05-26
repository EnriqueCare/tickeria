/**
 * TECNOLOGÍA: Spring Data JPA
 * PROPÓSITO: Interfaz de persistencia que hereda de JpaRepository. 
 * Proporciona de forma automática los métodos de acceso a datos (Save, Find, Delete) e implementa 
 * consultas derivadas personalizadas como 'findAllByOrderByOrdenAsc'.
 */
package tickeria_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tickeria_backend.model.Ticket;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    // Trae absolutamente todos los tickets ordenados por su posición
    List<Ticket> findAllByOrderByOrdenAsc();

    // ESTE ES EL CLAVE: Filtra los tickets de una pestaña específica (ej: "EN_PROGRESO") manteniendo su orden
    List<Ticket> findByEstadoOrderByOrdenAsc(String estado);
}