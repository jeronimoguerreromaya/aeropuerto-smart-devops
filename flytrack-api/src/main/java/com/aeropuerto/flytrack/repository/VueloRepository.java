package com.aeropuerto.flytrack.repository;

import com.aeropuerto.flytrack.domain.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Optional<Vuelo> findByNumeroVuelo(String numeroVuelo);

    List<Vuelo> findByEstado(String estado);

    List<Vuelo> findByOrigenAndDestino(String origen, String destino);
}
