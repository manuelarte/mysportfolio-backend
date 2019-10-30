package org.manuel.mysportfolio.services.command;

import org.manuel.mysportfolio.model.entities.user.AppUser;

public interface AppUserCommandService {

    AppUser save(AppUser appUser);

}
