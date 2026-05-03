package com.aeropuerto.flytrack.controller;

import com.aeropuerto.flytrack.dto.NotificacionDTO;
import com.aeropuerto.flytrack.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listarTodas() {
        return ResponseEntity.ok(notificacionService.listarTodas());
    }

    @GetMapping("/vuelo/{vueloId}")
    public ResponseEntity<List<NotificacionDTO>> listarPorVuelo(@PathVariable Long vueloId) {
        return ResponseEntity.ok(notificacionService.listarPorVuelo(vueloId));
    }
}
