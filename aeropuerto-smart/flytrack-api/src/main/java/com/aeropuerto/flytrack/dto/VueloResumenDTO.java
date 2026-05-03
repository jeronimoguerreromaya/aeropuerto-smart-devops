package com.aeropuerto.flytrack.dto;

import com.aeropuerto.flytrack.domain.Vuelo;
import lombok.Getter;

/**
 * DTO ligero para incluir info del vuelo dentro de otros DTOs
 * sin causar problemas de lazy loading.
 */
@Getter
public class VueloResumenDTO {
    private final Long id;
    private final String numeroVuelo;
    private final String origen;
    private final String destino;
    private final String puertaEmbarque;
    private final String estado;

    public VueloResumenDTO(Vuelo vuelo) {
        this.id = vuelo.getId();
        this.numeroVuelo = vuelo.getNumeroVuelo();
        this.origen = vuelo.getOrigen();
        this.destino = vuelo.getDestino();
        this.puertaEmbarque = vuelo.getPuertaEmbarque();
        this.estado = vuelo.getEstado();
    }
}
