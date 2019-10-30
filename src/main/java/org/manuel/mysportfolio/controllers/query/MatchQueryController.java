package org.manuel.mysportfolio.controllers.query;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProviderBySecurity;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
@AllArgsConstructor
public class MatchQueryController {

    private final MatchQueryService matchQueryService;
    private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;
    private final UserIdProviderBySecurity userIdProviderBySecurity;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MatchDto>> findByPage(
            @PageableDefault(sort = "startDate", direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(matchQueryService.findAllCreatedBy(pageable, getUserId()).map(matchToMatchDtoTransformer));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDto<TeamInMatchDto, TeamInMatchDto>> findOne(@PathVariable final String id) {
        // TODO, fix that if the user can't see the match
        final var match = matchQueryService.findOne(new ObjectId(id)).orElseThrow(() ->
                new IllegalArgumentException(String.format("Match with id %s not found", id)));
        return ResponseEntity.ok(matchToMatchDtoTransformer.apply(match));
    }

    private String getUserId() {
        return userIdProviderBySecurity.getUserId();
    }

}
