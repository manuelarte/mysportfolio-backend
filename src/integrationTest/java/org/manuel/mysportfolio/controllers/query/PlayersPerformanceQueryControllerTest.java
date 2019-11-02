package org.manuel.mysportfolio.controllers.query;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.manuel.mysportfolio.model.entities.match.PlayersPerformance;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.PlayersPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayersPerformanceQueryControllerTest {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayersPerformanceRepository playersPerformanceRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                // TODO CHECK TO REMOVE THAT, AND USE THE USER ID PROVIDER MAYBE
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        matchRepository.deleteAll();
        playersPerformanceRepository.deleteAll();
    }

    @Test
    public void testGetPlayerPerformance() throws Exception {
        final String createdBy = "123456789";

        final var matchSaved = matchRepository.save(TestUtils.createMockMatch(TestUtils.createMockAnonymousTeam(), TestUtils.createMockAnonymousTeam(), createdBy));
        playersPerformanceRepository.save(new PlayersPerformance(null, null, matchSaved.getId(), Collections.singletonMap(createdBy, new Performance(new BigDecimal("6.5"), null)),
                null, null, null, null));

        mvc.perform(get("/api/v1/matches/{matchId}/performances/{playerId}", matchSaved.getId(), createdBy)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value("6.5"));
    }

    @Test
    public void testGetPlayerPerformanceNotExisting() throws Exception {
        mvc.perform(get("/api/v1/matches/{matchId}/performances/{playerId}", new ObjectId(), "1234")
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }

}
