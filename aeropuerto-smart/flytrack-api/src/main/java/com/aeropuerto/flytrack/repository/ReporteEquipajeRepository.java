package com.aeropuerto.flytrack.repository;

import com.aeropuerto.flytrack.domain.ReporteEquipaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteEquipajeRepository extends JpaRepository<ReporteEquipaje, Long> {

    List<ReporteEquipaje> findByVueloId(Long vueloId);

    List<ReporteEquipaje> findByEstadoReclamo(String estadoReclamo);
}
