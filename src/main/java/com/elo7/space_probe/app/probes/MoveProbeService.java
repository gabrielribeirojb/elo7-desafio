package com.elo7.space_probe.app.probes;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Probes;
import com.elo7.space_probe.exceptions.CollisionException;
import com.elo7.space_probe.exceptions.EntityNotFoundException;
import com.elo7.space_probe.exceptions.InvalidCommandException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveProbeService {

    private final Probes probes;

    public MoveProbeService(Probes probes){
        this.probes = probes;
    }

    public Probe execute(Integer id, String commands) {
        if (id == null || commands == null) {
            throw new IllegalArgumentException("ID e comandos não podem ser nulos");
        }

        Optional<Probe> optionalProbe = probes.findById(id);

        if (optionalProbe.isEmpty()) {
            throw new EntityNotFoundException("Sonda não encontrada");
        }

        Probe probe = optionalProbe.get();

        for (char command : commands.toCharArray()) {
            switch (command) {
                case 'L' -> probe.turnLeft();
                case 'R' -> probe.turnRight();
                case 'M' -> {
                    if (isPositionOccupied(probe.getNextX(), probe.getNextY(), probe)) {
                        throw new CollisionException("Colisão detectada na posição: "
                                + probe.getNextX() + ", " + probe.getNextY());
                    }
                    probe.moveProbe();
                }
                default -> throw new InvalidCommandException("Comando inválido: " + command);
            }
        }

        return probes.save(probe);
    }

    private boolean isPositionOccupied(int x, int y, Probe currentProbe) {
        return probes.findAll().stream()
                .anyMatch(probe -> probe.getPlanetId().equals(currentProbe.getPlanetId()) &&
                        probe.getXPosition() == x &&
                        probe.getYPosition() == y &&
                        !probe.getId().equals(currentProbe.getId()));
    }
}
