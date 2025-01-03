package com.elo7.space_probe.app.probes;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Probes;
import com.elo7.space_probe.exceptions.CollisionException;
import com.elo7.space_probe.exceptions.OutOfBoundaryException;
import org.springframework.stereotype.Service;

@Service
public class CreateProbeService {
    private final Probes probes;

    CreateProbeService(Probes probes) {
        this.probes = probes;
    }

    public Probe execute(Probe probe) {
        if(probe.getPlanet().isPositionOccupied(probe.getPosition())){
            throw new CollisionException("Posição " + probe.getPosition() + "está ocupada");
        }

        if(!probe.getPlanet().isInPlanetBoundary(probe.getPosition())){
            throw new OutOfBoundaryException("Movimento fora dos limites do planeta");
        }

        return probes.save(probe);
    }

}
