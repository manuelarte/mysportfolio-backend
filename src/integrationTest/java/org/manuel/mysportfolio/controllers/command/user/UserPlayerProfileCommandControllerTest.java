package org.manuel.mysportfolio.controllers.command.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Year;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFootballInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.model.entities.player.FootballPosition;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.model.entities.user.AppSettings;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.PlayerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@Import(ItConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserPlayerProfileCommandControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private PlayerProfileRepository playerProfileRepository;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .build();
    appUserRepository.save(new AppUser(null, null, "test", "test@mymatchfolio.com",
        ItConfiguration.IT_USER_ID, AppMembership.FREE,
        false, null, new AppSettings(false), null, null, null, null));

  }

  @AfterEach
  public void tearDown() {
    playerProfileRepository.deleteAll();
    appUserRepository.deleteAll();
  }

  @Test
  public void updatePlayerProfileSportInfoForNotExistingPlayerTest() throws Exception {
    final var userId = ItConfiguration.IT_USER_ID;
    final var playerSportInfo = PlayerProfileSportInfoDto.builder()
        .footballInfo(PlayerProfileFootballInfoDto.builder()
            .preferredPosition(FootballPosition.CENTRE_FORWARD)
            .build())
        .build();

    mvc.perform(put("/api/v1/users/{userId}/player/{year}", userId, Year.now())
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(playerSportInfo)))
        .andExpect(status().isOk());
  }

  @Test
  public void updatePlayerProfileSportInfoForTheFuture() {
    final var userId = ItConfiguration.IT_USER_ID;
    final var playerSportInfo = PlayerProfileSportInfoDto.builder()
        .footballInfo(PlayerProfileFootballInfoDto.builder()
            .preferredPosition(FootballPosition.CENTRE_FORWARD)
            .build())
        .build();

    assertThrows(NestedServletException.class, () -> mvc.perform(
        put("/api/v1/users/{userId}/player/{year}", userId, Year.now().plusYears(1))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(playerSportInfo))));
  }

}
