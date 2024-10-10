package com.elo7.space_probe.ui.probes;

import com.elo7.space_probe.domain.Probe;

public record ProbeDTO(long id, String name, Integer x, Integer y, Integer planetId, String direction) {
    public ProbeDTO(Probe probe) {
        this(probe.getId(),
                probe.getName(),
                probe.getXPosition(),
                probe.getYPosition(),
                probe.getPlanetId(),
                probe.getDirection().name());
    }
}
