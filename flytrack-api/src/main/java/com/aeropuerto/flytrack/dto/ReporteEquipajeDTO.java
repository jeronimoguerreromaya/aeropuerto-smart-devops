package com.aeropuerto.flytrack.dto;

import com.aeropuerto.flytrack.domain.ReporteEquipaje;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO para serializar ReporteEquipaje sin problemas de lazy loading.
 */
@Getter
public class ReporteEquipajeDTO {
    private final Long id;
    private final String pasajeroNombre;
    private final String documentoPasajero;
    private final String email;
    private final String telefono;
    private final String descripcion;
    private final String estadoReclamo;
    private final LocalDateTime fechaReporte;
    private final VueloResumenDTO vuelo;

    public ReporteEquipajeDTO(ReporteEquipaje r) {
        this.id = r.getId();
        this.pasajeroNombre = r.getPasajeroNombre();
        this.documentoPasajero = r.getDocumentoPasajero();
        this.email = r.getEmail();
        this.telefono = r.getTelefono();
        this.descripcion = r.getDescripcion();
        this.estadoReclamo = r.getEstadoReclamo();
        this.fechaReporte = r.getFechaReporte();
        this.vuelo = r.getVuelo() != null ? new VueloResumenDTO(r.getVuelo()) : null;
    }
}
