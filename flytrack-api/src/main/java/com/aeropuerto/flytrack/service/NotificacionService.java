package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.dto.NotificacionDTO;
import com.aeropuerto.flytrack.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Transactional(readOnly = true)
    public List<NotificacionDTO> listarTodas() {
        // Al estar dentro de la transaccion, el lazy loading funciona correctamente
        return notificacionRepository.findAllByOrderByFechaEnvioDesc()
                .stream()
                .map(NotificacionDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificacionDTO> listarPorVuelo(Long vueloId) {
        return notificacionRepository.findByVueloIdOrderByFechaEnvioDesc(vueloId)
                .stream()
                .map(NotificacionDTO::new)
                .toList();
    }
}
