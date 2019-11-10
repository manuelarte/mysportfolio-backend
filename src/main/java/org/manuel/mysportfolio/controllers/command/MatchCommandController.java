package org.manuel.mysportfolio.controllers.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.MatchUpdateDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.services.command.MatchCommandService;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.transformers.match.MatchDtoToMatchTransformer;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.manuel.mysportfolio.transformers.match.MatchUpdateDtoToMatchTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/matches")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class MatchCommandController {

    private final MatchCommandService matchCommandService;
    private final MatchQueryService matchQueryService;
    private final MatchDtoToMatchTransformer matchDtoToMatchTransformer;
    private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;
    private final MatchUpdateDtoToMatchTransformer matchUpdateDtoToMatchTransformer;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDto<TeamTypeDto, TeamTypeDto>> saveMatch(
            @Validated({Default.class, NewEntity.class}) @RequestBody final MatchDto<TeamTypeDto, TeamTypeDto> matchDto) {
        final var saved = matchCommandService.save(matchDtoToMatchTransformer.apply(matchDto));

        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        log.info("Match with id {}, created by {} saved", saved.getId(), saved.getCreatedBy());
        return ResponseEntity.created(location).body(matchToMatchDtoTransformer.apply(saved));
    }

    @PutMapping(value = "/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDto<TeamTypeDto, TeamTypeDto>> updateMatch(
            @PathVariable final ObjectId matchId,
            @Validated({Default.class, UpdateEntity.class}) @RequestBody final MatchUpdateDto<TeamTypeDto, TeamTypeDto> matchDto) {
        final var originalMatch = matchQueryService.findOne(matchId).orElseThrow(() ->
                new EntityNotFoundException(Match.class, matchId.toString()));
        final var updated = matchCommandService.update(matchUpdateDtoToMatchTransformer.apply(originalMatch, matchDto));
        log.info("Match with id {}, updated by {}", updated.getId(), updated.getLastModifiedBy());
        return ResponseEntity.ok(matchToMatchDtoTransformer.apply(updated));
    }

}
