package org.manuel.mysportfolio.controllers.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/competitions")
@lombok.AllArgsConstructor
public class CompetitionQueryController {

    private final CompetitionQueryService competitionQueryService;
    private final CompetitionToCompetitionDtoTransformer competitionToCompetitionDtoTransformer;
    private final UserIdProvider userIdProvider;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CompetitionDto>> findByPage(
            @PageableDefault final Pageable pageable, @RequestParam(required = false, value = "sport") final Sport sport) {
        return ResponseEntity.ok(competitionQueryService.findAllCreatedBy(pageable, getUserId()).map(competitionToCompetitionDtoTransformer));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompetitionDto> findOne(@PathVariable final ObjectId id) {
        // TODO, fix that if the user can't see the competition
        final var competition = competitionQueryService.findOne(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Competition with id %s not found", id)));
        return ResponseEntity.ok(competitionToCompetitionDtoTransformer.apply(competition));
    }

    private String getUserId() {
        return userIdProvider.getUserId();
    }

}
