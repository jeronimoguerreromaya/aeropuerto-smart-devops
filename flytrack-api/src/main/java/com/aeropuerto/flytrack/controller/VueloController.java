package com.aeropuerto.flytrack.controller;

import com.aeropuerto.flytrack.domain.Vuelo;
import com.aeropuerto.flytrack.service.VueloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vuelos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VueloController {

    private final VueloService vueloService;

    @GetMapping
    public ResponseEntity<List<Vuelo>> listarVuelos() {
        return ResponseEntity.ok(vueloService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> obtenerVuelo(@PathVariable Long id) {
        return ResponseEntity.ok(vueloService.buscarPorId(id));
    }

    @GetMapping("/numero/{numeroVuelo}")
    public ResponseEntity<Vuelo> obtenerPorNumero(@PathVariable String numeroVuelo) {
        return ResponseEntity.ok(vueloService.buscarPorNumero(numeroVuelo));
    }

    @PostMapping
    public ResponseEntity<Vuelo> crearVuelo(@Valid @RequestBody Vuelo vuelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vueloService.crearVuelo(vuelo));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Vuelo> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        String puertaEmbarque = body.get("puertaEmbarque");
        return ResponseEntity.ok(vueloService.actualizarEstado(id, nuevoEstado, puertaEmbarque));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVuelo(@PathVariable Long id) {
        vueloService.eliminarVuelo(id);
        return ResponseEntity.noContent().build();
    }
}
