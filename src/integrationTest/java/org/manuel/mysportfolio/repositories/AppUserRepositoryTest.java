package org.manuel.mysportfolio.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import io.github.manuelarte.mysportfolio.model.documents.user.AppSettings;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import javax.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;

@DataMongoTest
@AutoConfigurationPackage
class AppUserRepositoryTest {

  @Inject
  private AppUserRepository appUserRepository;

  @Test
  @DisplayName("Test not duplicated external id allowed")
  public void testNotDuplicatedExternalId() {
    final var saved = appUserRepository.save(
        new AppUser("test", "test@mymatchfolio.com", "externalId", AppMembership.FREE,
            false, null, new AppSettings(false)));

    final var newAppUser = new AppUser("test1", "test1@mymatchfolio.com",
        saved.getExternalId(), AppMembership.FREE,
        false, null, new AppSettings(false));
    assertThrows(DuplicateKeyException.class, () -> appUserRepository.save(newAppUser));
  }
}
