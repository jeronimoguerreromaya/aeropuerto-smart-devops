package com.aeropuerto.flytrack.dto;

import com.aeropuerto.flytrack.domain.Notificacion;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO para serializar Notificacion sin problemas de lazy loading.
 */
@Getter
public class NotificacionDTO {
    private final Long id;
    private final String mensaje;
    private final String tipo;
    private final LocalDateTime fechaEnvio;
    private final VueloResumenDTO vuelo;

    public NotificacionDTO(Notificacion n) {
        this.id = n.getId();
        this.mensaje = n.getMensaje();
        this.tipo = n.getTipo();
        this.fechaEnvio = n.getFechaEnvio();
        this.vuelo = n.getVuelo() != null ? new VueloResumenDTO(n.getVuelo()) : null;
    }
}
