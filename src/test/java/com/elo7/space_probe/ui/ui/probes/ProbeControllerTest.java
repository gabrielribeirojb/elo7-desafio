package com.elo7.space_probe.ui.ui.probes;

import com.elo7.space_probe.app.probes.CreateProbeService;
import com.elo7.space_probe.app.probes.MoveProbeService;
import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Probe;
import com.elo7.space_probe.domain.enums.Direction;
import com.elo7.space_probe.exceptions.EntityNotFoundException;
import com.elo7.space_probe.exceptions.OutOfBoundaryException;
import com.elo7.space_probe.infra.repository.DataSourcePlanetRepository;
import com.elo7.space_probe.infra.repository.DataSourceProbeRepository;
import com.elo7.space_probe.ui.probes.ProbeCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProbeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateProbeService createProbeService;

    @Autowired
    private MoveProbeService moveProbeService;

    @Autowired
    private DataSourcePlanetRepository planetRepository;

    @Autowired
    private DataSourceProbeRepository probeRepository;

    private Probe probe;
    private Planet planet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        planetRepository.deleteAll();
        probeRepository.deleteAll();

        planet = new Planet("Marte", 5, 5);
        planet = planetRepository.save(planet);

        probe = new Probe("Sonda 1", 1, 2, Direction.NORTH, planet);
        probe = probeRepository.save(probe);
    }

    @Test
    void shouldCreateProbe() throws Exception {
        ProbeCreateDTO probeCreateDTO = new ProbeCreateDTO("Sonda 1", 1, 2, Direction.NORTH, planet.getId());

        given(createProbeService.execute(any(Probe.class))).willReturn(probe);

        mockMvc.perform(post("/v1/probes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(probeCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sonda 1"))
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(2));
    }

    @Test
    void shouldMoveProbeSuccessfully() throws Exception {
        mockMvc.perform(post("/v1/probes/" + probe.getId() + "/move")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("LMLMLMLMM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(3))
                .andExpect(jsonPath("$.direction").value("NORTH"));
    }

    @Test
    void shouldReturnEntityNotFoundWhenProbeDoesNotExist() throws Exception {
        mockMvc.perform(post("/v1/probes/999/move")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("LMLM"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Sonda não encontrada"));
    }

    @Test
    void shouldReturnBadRequestForOutOfBoundaryMovement() throws Exception {
        assertNotNull(probeRepository.findById(probe.getId()));

        mockMvc.perform(post("/v1/probes/" + probe.getId() + "/move")
                        .content("MMMMMMMM")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Movimento fora dos limites do planeta"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}
