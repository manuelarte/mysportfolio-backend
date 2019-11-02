package org.manuel.mysportfolio.controllers.command;

import lombok.AllArgsConstructor;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;
import org.manuel.mysportfolio.services.command.MatchCommandService;
import org.manuel.mysportfolio.transformers.match.MatchDtoToMatchTransformer;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/matches")
@AllArgsConstructor
public class MatchCommandController {

    private final MatchCommandService matchCommandService;
    private final MatchDtoToMatchTransformer matchDtoToMatchTransformer;
    private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDto<TeamInMatchDto, TeamInMatchDto>> saveMatch(
            @Validated({Default.class, NewEntity.class}) @RequestBody final MatchDto<TeamInMatchDto, TeamInMatchDto> matchDto) {
        final var saved = matchCommandService.save(matchDtoToMatchTransformer.apply(matchDto));

        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(matchToMatchDtoTransformer.apply(saved));
    }

}
