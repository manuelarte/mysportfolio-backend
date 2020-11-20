package org.manuel.mysportfolio.controllers.command.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.documents.player.FootballPosition;
import java.time.Year;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFootballInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.model.entities.user.AppSettings;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.PlayerProfileRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserPlayerProfileCommandControllerTest {

  @Inject
  private ObjectMapper objectMapper;

  @Inject
  private AppUserRepository appUserRepository;

  @Inject
  private PlayerProfileRepository playerProfileRepository;

  @Inject
  private MockMvc mvc;

  @BeforeEach
  @SuppressWarnings("checkstyle:javadoctype")
  public void setup() {
    appUserRepository.save(new AppUser("test", "test@mymatchfolio.com",
        ItConfiguration.IT_USER_ID, AppMembership.FREE, false, null,
        new AppSettings(false)));
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

  @Test
  public void updatePlayerProfileForNonExistingUser() {
    final var userId = ItConfiguration.IT_USER_ID + "nono";
    final var playerSportInfo = PlayerProfileSportInfoDto.builder()
        .footballInfo(PlayerProfileFootballInfoDto.builder()
            .preferredPosition(FootballPosition.CENTRE_FORWARD)
            .build())
        .build();

    final var thrown = assertThrows(NestedServletException.class, () -> mvc.perform(
        put("/api/v1/users/{userId}/player/{year}", userId, Year.now().minusYears(1))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(playerSportInfo))));
    assertTrue(thrown.getCause() instanceof ConstraintViolationException);
  }

}
