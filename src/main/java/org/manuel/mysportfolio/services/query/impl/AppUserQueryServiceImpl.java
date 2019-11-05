package org.manuel.mysportfolio.services.query.impl;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@lombok.AllArgsConstructor
class AppUserQueryServiceImpl implements AppUserQueryService {

    private final AppUserRepository appUserRepository;

    @Override
    public Optional<AppUser> findOne(final ObjectId id) {
        return appUserRepository.findById(id);
    }

    @Override
    public Optional<AppUser> findByExternalId(final String externalId) {
        return appUserRepository.findByExternalId(externalId);
    }

    @Override
    public Page<AppUser> findAll(final Pageable pageable) {
        return appUserRepository.findAll(pageable);
    }

}
