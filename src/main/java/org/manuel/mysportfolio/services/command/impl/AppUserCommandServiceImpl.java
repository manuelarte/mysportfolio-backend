package org.manuel.mysportfolio.services.command.impl;

import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@lombok.AllArgsConstructor
public class AppUserCommandServiceImpl implements AppUserCommandService {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser save(@NotNull final AppUser appUser) {
        return appUserRepository.save(appUser);
    }
}
