package com.elo7.space_probe.app.probes;

import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Probes;
import com.elo7.space_probe.exceptions.EntityNotFoundException;
import com.elo7.space_probe.exceptions.InvalidCommandException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveProbeService {

    private final Probes probes;

    MoveProbeService(Probes probes){
        this.probes = probes;
    }

    public Probe execute(Integer id, String commands) {
        Optional<Probe> optionalProbe = probes.findById(id);

        if (optionalProbe.isEmpty()) {
            throw new EntityNotFoundException("Sonda não encontrada");
        }

        Probe probe = optionalProbe.get();

        for (char command : commands.toCharArray()) {
            switch (command) {
                case 'L' -> probe.turnLeft();
                case 'R' -> probe.turnRight();
                case 'M' -> probe.moveProbe();
                default -> throw new InvalidCommandException("Comando inválido: " + command);
            }
        }

        return probes.save(probe);
    }
}
