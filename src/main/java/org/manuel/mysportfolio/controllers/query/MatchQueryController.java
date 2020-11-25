package org.manuel.mysportfolio.controllers.query;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.spring.queryparameter.QueryParameter;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
@lombok.AllArgsConstructor
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MatchQueryController {

  private final MatchQueryService matchQueryService;
  private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;
  private final UserIdProvider userIdProvider;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<MatchDto<TeamTypeDto, TeamTypeDto>>> findByPage(
      @PageableDefault(sort = "startDate", direction = Sort.Direction.DESC) final Pageable pageable,
      @QueryParameter(entity = Match.class) final Optional<Query> query) {
    final Page<Match<TeamType, TeamType>> page;
    if (query.isPresent()) {
      page = matchQueryService.findAllBy(query.get(), pageable, getUserId());
    } else {
      page = matchQueryService.findAllCreatedBy(getUserId(), pageable);
    }
    return ResponseEntity.ok(page.map(matchToMatchDtoTransformer));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MatchDto<TeamTypeDto, TeamTypeDto>> findOne(
      @PathVariable final ObjectId id) {
    // TODO, fix that if the user can't see the match
    final var match = matchQueryService.findOne(id).orElseThrow(() ->
        new EntityNotFoundException(Match.class, id.toString()));
    return ResponseEntity.ok(matchToMatchDtoTransformer.apply(match));
  }

  private String getUserId() {
    return userIdProvider.getUserId();
  }

}
