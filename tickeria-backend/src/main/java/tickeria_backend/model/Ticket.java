
/**
 * TECNOLOGÍA: JPA (Jakarta Persistence) / Hibernate ORM
 * PROPÓSITO: Clase Entidad que mapea directamente un objeto Java con la tabla 'tickets' en MySQL.
 * Define las columnas físicas de la base de datos (id, titulo, descripcion, cliente, prioridad, estado, orden).
 * Hibernate (ORM) para mapear (vincular) tu código de Java directamente con las tablas de tla base de datos en Docker
 */
package tickeria_backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // que autosuba OJO poir si batallo en inserts!
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT") //Para que que no tenga limite por defecto
    private String descripcion;

    @Column(nullable = false)
    private String estado; // Guardará: "POR_HACER", "EN_PROGRESO" o "COMPLETADO"

    @Column(nullable = false)
    private String prioridad; // Guardará: "ALTA", "MEDIA" o "BAJA"

    @Column(name = "orden_posicion")
    private Integer orden; // Crucial para recordar cómo los acomoda al arrastrar

    @Column(nullable = false)
    private String cliente;

    // Construtores
    public Ticket() {}

    public Ticket(String titulo, String descripcion, String estado, String prioridad, Integer orden, String cliente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.orden = orden;
        this.cliente = cliente;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
}