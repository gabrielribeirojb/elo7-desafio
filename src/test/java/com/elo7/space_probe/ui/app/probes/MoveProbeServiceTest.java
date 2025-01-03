package com.elo7.space_probe.ui.app.probes;

import com.elo7.space_probe.app.probes.MoveProbeService;
import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.Probes;
import com.elo7.space_probe.domain.enums.Direction;
import com.elo7.space_probe.exceptions.EntityNotFoundException;
import com.elo7.space_probe.exceptions.InvalidCommandException;
import com.elo7.space_probe.exceptions.OutOfBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MoveProbeServiceTest {

    private Probes probes;
    private MoveProbeService moveProbeServiceSubject;
    private Planet planet;
    private Probe probe;

    @BeforeEach
    void setUp() {
        probes = mock(Probes.class);
        moveProbeServiceSubject = new MoveProbeService(probes);
        planet = new Planet("Marte", 5, 5);
        probe = new Probe("Sonda 1", 1, 2, Direction.NORTH, planet);
    }

    @Test
    void shouldMoveProbeCorrectly() {
        when(probes.findById(1)).thenReturn(Optional.of(probe));
        when(probes.save(any(Probe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Probe movedProbe = moveProbeServiceSubject.execute(1, "LMLMLMLMM");

        assertNotNull(movedProbe, "A sonda movida não deveria ser nula");
        assertEquals(1, movedProbe.getXPosition());
        assertEquals(3, movedProbe.getYPosition());
        assertEquals(Direction.NORTH, movedProbe.getDirection());
    }

    @Test
    void shouldThrowEntityNotFoundException() {
        when(probes.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> moveProbeServiceSubject.execute(1, "LMLM"));
    }

    @Test
    void shouldThrowInvalidCommandException() {
        when(probes.findById(1)).thenReturn(Optional.of(probe));
        assertThrows(InvalidCommandException.class, () -> moveProbeServiceSubject.execute(1, "Z"));
    }

    @Test
    void shouldThrowOutOfBoundaryException() {
        when(probes.findById(1)).thenReturn(Optional.of(probe));
        assertThrows(OutOfBoundaryException.class, () -> moveProbeServiceSubject.execute(1, "MMMMMMMM"));
    }

}
