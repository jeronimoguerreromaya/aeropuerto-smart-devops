package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.domain.ReporteEquipaje;
import com.aeropuerto.flytrack.domain.Vuelo;
import com.aeropuerto.flytrack.dto.ReporteEquipajeDTO;
import com.aeropuerto.flytrack.repository.ReporteEquipajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del servicio de Reportes de Equipaje")
class ReporteEquipajeServiceTest {

    @Mock
    private ReporteEquipajeRepository reporteRepository;

    @Mock
    private VueloService vueloService;

    @InjectMocks
    private ReporteEquipajeService reporteService;

    private Vuelo vueloEjemplo;
    private ReporteEquipaje reporteEjemplo;

    @BeforeEach
    void setUp() {
        vueloEjemplo = Vuelo.builder()
                .id(1L)
                .numeroVuelo("AV101")
                .origen("Bogotá")
                .destino("Medellín")
                .estado("PROGRAMADO")
                .build();

        reporteEjemplo = ReporteEquipaje.builder()
                .id(1L)
                .pasajeroNombre("Juan Pérez")
                .documentoPasajero("123456789")
                .email("juan@email.com")
                .telefono("3001234567")
                .descripcion("Maleta perdida")
                .vuelo(vueloEjemplo)
                .estadoReclamo("PENDIENTE")
                .build();
    }

    @Test
    @DisplayName("Debe crear un reporte de equipaje correctamente")
    void debeCrearReporte() {
        when(vueloService.buscarPorId(1L)).thenReturn(vueloEjemplo);
        when(reporteRepository.save(any(ReporteEquipaje.class))).thenReturn(reporteEjemplo);

        // crearReporte ahora recibe: nombre, documento, email, telefono, descripcion, vueloId
        ReporteEquipajeDTO resultado = reporteService.crearReporte(
                "Juan Pérez", "123456789", "juan@email.com", "3001234567", "Maleta perdida", 1L);

        assertThat(resultado.getPasajeroNombre()).isEqualTo("Juan Pérez");
        assertThat(resultado.getEstadoReclamo()).isEqualTo("PENDIENTE");
        verify(reporteRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Debe actualizar el estado del reclamo")
    void debeActualizarEstadoReclamo() {
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporteEjemplo));
        when(reporteRepository.save(any(ReporteEquipaje.class))).thenAnswer(i -> i.getArgument(0));

        // actualizarEstado retorna ReporteEquipajeDTO
        ReporteEquipajeDTO resultado = reporteService.actualizarEstado(1L, "EN_PROCESO");

        assertThat(resultado.getEstadoReclamo()).isEqualTo("EN_PROCESO");
    }

    @Test
    @DisplayName("Debe lanzar excepcion si el reporte no existe")
    void debeLanzarExcepcionSiReporteNoExiste() {
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reporteService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }
}
