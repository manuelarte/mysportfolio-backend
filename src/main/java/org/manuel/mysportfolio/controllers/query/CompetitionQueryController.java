package org.manuel.mysportfolio.controllers.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/competitions")
@lombok.AllArgsConstructor
public class CompetitionQueryController {

    private final CompetitionQueryService competitionQueryService;
    private final CompetitionToCompetitionDtoTransformer competitionToCompetitionDtoTransformer;
    private final UserIdProvider userIdProvider;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CompetitionDto>> findByPage(
            @PageableDefault final Pageable pageable) {
        return ResponseEntity.ok(competitionQueryService.findAllCreatedBy(pageable, getUserId()).map(competitionToCompetitionDtoTransformer));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompetitionDto> findOne(@PathVariable final String id) {
        // TODO, fix that if the user can't see the competition
        final var competition = competitionQueryService.findOne(new ObjectId(id)).orElseThrow(() ->
                new IllegalArgumentException(String.format("Competition with id %s not found", id)));
        return ResponseEntity.ok(competitionToCompetitionDtoTransformer.apply(competition));
    }

    private String getUserId() {
        return userIdProvider.getUserId();
    }

}
