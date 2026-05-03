package com.aeropuerto.flytrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entidad que representa un vuelo en el sistema FlyTrack.
 */
@Entity
@Table(name = "vuelos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de vuelo es obligatorio")
    @Size(max = 10)
    @Column(name = "numero_vuelo", unique = true, nullable = false, length = 10)
    private String numeroVuelo;

    @NotBlank(message = "El origen es obligatorio")
    @Size(max = 50)
    @Column(length = 50)
    private String origen;

    @NotBlank(message = "El destino es obligatorio")
    @Size(max = 50)
    @Column(length = 50)
    private String destino;

    @Size(max = 10)
    @Column(name = "puerta_embarque", length = 10)
    private String puertaEmbarque;

    /**
     * Estado del vuelo: PROGRAMADO, RETRASADO, CANCELADO, EMBARCANDO
     */
    @Column(length = 20)
    @Builder.Default
    private String estado = "PROGRAMADO";
}
