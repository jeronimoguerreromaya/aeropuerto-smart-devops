package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.domain.Notificacion;
import com.aeropuerto.flytrack.domain.Vuelo;
import com.aeropuerto.flytrack.repository.NotificacionRepository;
import com.aeropuerto.flytrack.repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VueloService {

    private final VueloRepository vueloRepository;
    private final NotificacionRepository notificacionRepository;

    public List<Vuelo> listarTodos() {
        return vueloRepository.findAll();
    }

    public Vuelo buscarPorId(Long id) {
        return vueloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con id: " + id));
    }

    public Vuelo buscarPorNumero(String numeroVuelo) {
        return vueloRepository.findByNumeroVuelo(numeroVuelo)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado: " + numeroVuelo));
    }

    @Transactional
    public Vuelo crearVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    /**
     * Actualiza el estado de un vuelo y genera una notificacion automatica.
     */
    @Transactional
    public Vuelo actualizarEstado(Long id, String nuevoEstado, String puertaEmbarque) {
        Vuelo vuelo = buscarPorId(id);
        String estadoAnterior = vuelo.getEstado();
        vuelo.setEstado(nuevoEstado);

        if (puertaEmbarque != null && !puertaEmbarque.isBlank()) {
            vuelo.setPuertaEmbarque(puertaEmbarque);
        }

        vueloRepository.save(vuelo);

        // Generar notificacion si el estado cambio
        if (!estadoAnterior.equals(nuevoEstado)) {
            String tipo = resolverTipoNotificacion(nuevoEstado);
            String mensaje = String.format("El vuelo %s ha cambiado de estado: %s → %s",
                    vuelo.getNumeroVuelo(), estadoAnterior, nuevoEstado);

            Notificacion notificacion = Notificacion.builder()
                    .vuelo(vuelo)
                    .mensaje(mensaje)
                    .tipo(tipo)
                    .build();
            notificacionRepository.save(notificacion);
        }

        return vuelo;
    }

    private String resolverTipoNotificacion(String estado) {
        return switch (estado.toUpperCase()) {
            case "RETRASADO" -> "RETRASO";
            case "CANCELADO" -> "CANCELACION";
            case "EMBARCANDO" -> "EMBARQUE";
            default -> "CAMBIO_PUERTA";
        };
    }

    @Transactional
    public void eliminarVuelo(Long id) {
        vueloRepository.deleteById(id);
    }
}
