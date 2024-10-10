package com.elo7.space_probe.app.probes;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Probes;
import com.elo7.space_probe.exceptions.CollisionException;
import org.springframework.stereotype.Service;

@Service
public class CreateProbeService {
    private final Probes probes;

    CreateProbeService(Probes probes) {
        this.probes = probes;
    }

    public Probe execute(Probe probe) {
        boolean isCollision = probes.existsByPlanetAndPosition_XAndPosition_Y(
                probe.getPlanet(),
                probe.getXPosition(),
                probe.getYPosition()
        );

        if (isCollision) {
            throw new CollisionException("Já existe uma sonda na posição (" + probe.getXPosition() + ", " + probe.getYPosition() + ") no planeta " + probe.getPlanet().getName());
        }

        return probes.save(probe);
    }

}
