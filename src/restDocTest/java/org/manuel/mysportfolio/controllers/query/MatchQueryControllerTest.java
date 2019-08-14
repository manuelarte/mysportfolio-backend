package org.manuel.mysportfolio.controllers.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class MatchQueryControllerTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @AfterEach
    public void tearDown() {
        matchRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void getAllMatches() throws Exception {
        final var teamSaved = teamRepository.save(TestUtils.createMockTeam());
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setTeamId(teamSaved.getId());

        matchRepository.save(TestUtils.createMockMatch(registeredTeam, TestUtils.createMockAnonymousTeam()));
        matchRepository.save(TestUtils.createMockMatch(TestUtils.createMockAnonymousTeam(), registeredTeam));

        this.mockMvc.perform(get("/api/v1/matches"))
                .andExpect(status().isOk())
                .andDo(document("getAllMatches", preprocessResponse(prettyPrint())));
    }


    @Test
    public void getOneMatch() throws Exception {
        final var teamSaved = teamRepository.save(TestUtils.createMockTeam());
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setTeamId(teamSaved.getId());

        final var saved = matchRepository.save(TestUtils.createMockMatch(registeredTeam, TestUtils.createMockAnonymousTeam()));
        this.mockMvc.perform(get("/api/v1/matches/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andDo(document("getMatch", preprocessResponse(prettyPrint())));
    }

}
