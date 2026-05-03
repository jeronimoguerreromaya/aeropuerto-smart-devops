package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.domain.ReporteEquipaje;
import com.aeropuerto.flytrack.domain.Vuelo;
import com.aeropuerto.flytrack.dto.ReporteEquipajeDTO;
import com.aeropuerto.flytrack.repository.ReporteEquipajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteEquipajeService {

    private final ReporteEquipajeRepository reporteRepository;
    private final VueloService vueloService;

    @Transactional(readOnly = true)
    public List<ReporteEquipajeDTO> listarTodos() {
        return reporteRepository.findAll()
                .stream()
                .map(ReporteEquipajeDTO::new)
                .toList();
    }

    public ReporteEquipaje buscarPorId(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
    }

    @Transactional
    public ReporteEquipajeDTO crearReporte(String pasajeroNombre, String documentoPasajero,
                                           String email, String telefono,
                                           String descripcion, Long vueloId) {
        Vuelo vuelo = vueloService.buscarPorId(vueloId);

        ReporteEquipaje reporte = ReporteEquipaje.builder()
                .pasajeroNombre(pasajeroNombre)
                .documentoPasajero(documentoPasajero)
                .email(email)
                .telefono(telefono)
                .descripcion(descripcion)
                .vuelo(vuelo)
                .build();

        return new ReporteEquipajeDTO(reporteRepository.save(reporte));
    }

    @Transactional
    public ReporteEquipajeDTO actualizarEstado(Long id, String nuevoEstado) {
        ReporteEquipaje reporte = buscarPorId(id);
        reporte.setEstadoReclamo(nuevoEstado);
        return new ReporteEquipajeDTO(reporteRepository.save(reporte));
    }
}
