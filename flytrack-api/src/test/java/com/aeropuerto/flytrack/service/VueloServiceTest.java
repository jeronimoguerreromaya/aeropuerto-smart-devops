package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.domain.Vuelo;
import com.aeropuerto.flytrack.repository.NotificacionRepository;
import com.aeropuerto.flytrack.repository.VueloRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del servicio de Vuelos")
class VueloServiceTest {

    @Mock
    private VueloRepository vueloRepository;

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private VueloService vueloService;

    private Vuelo vueloEjemplo;

    @BeforeEach
    void setUp() {
        vueloEjemplo = Vuelo.builder()
                .id(1L)
                .numeroVuelo("AV101")
                .origen("Bogotá")
                .destino("Medellín")
                .puertaEmbarque("A3")
                .estado("PROGRAMADO")
                .build();
    }

    @Test
    @DisplayName("Debe listar todos los vuelos correctamente")
    void debeListarTodosLosVuelos() {
        when(vueloRepository.findAll()).thenReturn(List.of(vueloEjemplo));

        List<Vuelo> resultado = vueloService.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNumeroVuelo()).isEqualTo("AV101");
        verify(vueloRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un vuelo por ID")
    void debeEncontrarVueloPorId() {
        when(vueloRepository.findById(1L)).thenReturn(Optional.of(vueloEjemplo));

        Vuelo resultado = vueloService.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getOrigen()).isEqualTo("Bogotá");
    }

    @Test
    @DisplayName("Debe lanzar excepcion si el vuelo no existe")
    void debeLanzarExcepcionSiVueloNoExiste() {
        when(vueloRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vueloService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Debe crear un vuelo y guardarlo")
    void debeCrearVuelo() {
        when(vueloRepository.save(any(Vuelo.class))).thenReturn(vueloEjemplo);

        Vuelo resultado = vueloService.crearVuelo(vueloEjemplo);

        assertThat(resultado.getNumeroVuelo()).isEqualTo("AV101");
        verify(vueloRepository, times(1)).save(vueloEjemplo);
    }

    @Test
    @DisplayName("Debe actualizar estado y generar notificacion")
    void debeActualizarEstadoYGenerarNotificacion() {
        when(vueloRepository.findById(1L)).thenReturn(Optional.of(vueloEjemplo));
        when(vueloRepository.save(any(Vuelo.class))).thenReturn(vueloEjemplo);
        when(notificacionRepository.save(any())).thenReturn(null);

        Vuelo resultado = vueloService.actualizarEstado(1L, "RETRASADO", "B5");

        assertThat(resultado.getEstado()).isEqualTo("RETRASADO");
        // Verifica que se guardo la notificacion
        verify(notificacionRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("No debe generar notificacion si el estado no cambia")
    void noDebeGenerarNotificacionSiEstadoIgual() {
        when(vueloRepository.findById(1L)).thenReturn(Optional.of(vueloEjemplo));
        when(vueloRepository.save(any(Vuelo.class))).thenReturn(vueloEjemplo);

        vueloService.actualizarEstado(1L, "PROGRAMADO", null);

        // El estado es el mismo, no debe guardar notificacion
        verify(notificacionRepository, never()).save(any());
    }
}
