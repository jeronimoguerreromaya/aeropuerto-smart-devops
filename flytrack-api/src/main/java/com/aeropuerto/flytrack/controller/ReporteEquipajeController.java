package com.aeropuerto.flytrack.controller;

import com.aeropuerto.flytrack.dto.ReporteEquipajeDTO;
import com.aeropuerto.flytrack.service.ReporteEquipajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReporteEquipajeController {

    private final ReporteEquipajeService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteEquipajeDTO>> listarReportes() {
        return ResponseEntity.ok(reporteService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ReporteEquipajeDTO> crearReporte(@RequestBody Map<String, Object> body) {
        String pasajeroNombre    = (String) body.get("pasajeroNombre");
        String documentoPasajero = (String) body.get("documentoPasajero");
        String email             = (String) body.get("email");
        String telefono          = (String) body.get("telefono");
        String descripcion       = (String) body.get("descripcion");
        Long   vueloId           = Long.valueOf(body.get("vueloId").toString());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reporteService.crearReporte(
                        pasajeroNombre, documentoPasajero,
                        email, telefono,
                        descripcion, vueloId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReporteEquipajeDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(reporteService.actualizarEstado(id, body.get("estadoReclamo")));
    }
}
