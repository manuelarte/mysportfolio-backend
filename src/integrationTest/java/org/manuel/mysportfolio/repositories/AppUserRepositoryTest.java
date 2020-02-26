package org.manuel.mysportfolio.repositories;

import javax.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.model.entities.user.AppSettings;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class AppUserRepositoryTest {

    @Inject
    private AppUserRepository appUserRepository;

    @DisplayName("Test not duplicated external id allowed")
    @Test
    public void testNotDuplicatedExternalId() {
        final var saved = appUserRepository.save(new AppUser(null, null, "test", "test@mymatchfolio.com", "externalId", AppMembership.FREE,
                false, null, new AppSettings(false), null, null, null, null));

        final var newAppUser = new AppUser(null, null, "test1", "test1@mymatchfolio.com", saved.getExternalId(), AppMembership.FREE,
                false, null, new AppSettings(false), null, null, null, null);
        assertThrows(DuplicateKeyException.class, () -> appUserRepository.save(newAppUser));
    }
}
