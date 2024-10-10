package com.elo7.space_probe.ui.domain;

import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.enums.Direction;
import com.elo7.space_probe.exceptions.OutOfBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProbeTest {

    private Planet planet;
    private Probe probe;

    @BeforeEach
    void setUp() {
        planet = new Planet("Jupiter", 5, 5);
        probe = new Probe("Sonda 1", 1, 2, Direction.NORTH, planet);
    }

    @Test
    void shouldMoveProbeNorth() {
        probe.moveProbe();
        assertEquals(3, probe.getYPosition());
        assertEquals(1, probe.getXPosition());
        assertEquals(Direction.NORTH, probe.getDirection());
    }

    @Test
    void shouldTurnProbeRight() {
        probe.turnRight();
        assertEquals(Direction.EAST, probe.getDirection());
    }

    @Test
    void shouldTurnProbeLeft() {
        probe.turnLeft();
        assertEquals(Direction.WEST, probe.getDirection());
    }

    @Test
    void shouldThrowExceptionWhenMovingOutOfBoundaries() {
        assertThrows(OutOfBoundaryException.class, () -> {
            while (true) {
                probe.moveProbe();
            }
        });
    }
}

