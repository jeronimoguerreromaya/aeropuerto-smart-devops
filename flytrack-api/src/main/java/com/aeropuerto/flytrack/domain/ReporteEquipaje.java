package com.aeropuerto.flytrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa un reporte de inconveniente con equipaje.
 */
@Entity
@Table(name = "reportes_equipaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteEquipaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del pasajero es obligatorio")
    @Column(name = "pasajero_nombre", length = 100)
    private String pasajeroNombre;

    /** Cédula o pasaporte — único por pasajero */
    @NotBlank(message = "El documento del pasajero es obligatorio")
    @Column(name = "documento_pasajero", length = 20)
    private String documentoPasajero;

    @Email(message = "El email no tiene formato válido")
    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @Column(name = "fecha_reporte")
    @Builder.Default
    private LocalDateTime fechaReporte = LocalDateTime.now();

    /**
     * Estado del reclamo: PENDIENTE, EN_PROCESO, RESUELTO
     */
    @Column(length = 20)
    @Builder.Default
    private String estadoReclamo = "PENDIENTE";
}
