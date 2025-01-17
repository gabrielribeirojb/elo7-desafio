package com.elo7.space_probe.infra.repository;

import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Probe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProbeRepository extends JpaRepository<Probe, Integer> {
    boolean existsByPlanetAndPosition_XAndPosition_Y(Planet planet, int x, int y);
}
