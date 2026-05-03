package com.aeropuerto.flytrack.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa una notificación de cambio de vuelo.
 */
@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @Column(name = "fecha_envio")
    @Builder.Default
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    /**
     * Tipo: RETRASO, CANCELACION, CAMBIO_PUERTA, EMBARQUE
     */
    @Column(length = 20)
    private String tipo;
}
