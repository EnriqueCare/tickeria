/**

 * TECNOLOGÍA: Spring Boot Component Lifecycle
 * PROPÓSITO: Semilla de datos (Data Seeding). Se ejecuta automáticamente al arrancar el servidor de Java.
 * Verifica si la base de datos está vacía e inyecta tickets iniciales de prueba para desarrollo limpio.
 * Esta madre es nomas pa checar si le entra y tambien los datos :pppp
 */

package tickeria_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tickeria_backend.model.Ticket;
import tickeria_backend.repository.TicketRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(TicketRepository repository) {
        return args -> {
            // Solo se ñinserta si la base de datos está vacía
            if (repository.count() == 0) {
                System.out.println("🚀 Insertando tickets de prueba iniciales en Docker...");
                
                // Columnas: Titulo, Descripcion, Estado, Prioridad, Orden, Cliente
                repository.save(new Ticket("Error pasarela de pagos", "Falla el token de transacciones", "POR_HACER", "ALTA", 1, "Cinemex"));
                repository.save(new Ticket("Configurar respaldos DB", "Programar backups semanales en AWS", "POR_HACER", "MEDIA", 2, "Oxxo"));
                repository.save(new Ticket("Endpoint de autenticación", "Crear las rutas JWT de seguridad", "EN_PROGRESO", "ALTA", 1, "Interno"));
                repository.save(new Ticket("Diseño de la interfaz de Login", "Maquetar la tarjeta con Tailwind y efectos", "COMPLETADO", "BAJA", 1, "Tickeria"));
                
                System.out.println("✅ ¡Tickets de prueba cargados exitosamente!");
            }
        };
    }
}