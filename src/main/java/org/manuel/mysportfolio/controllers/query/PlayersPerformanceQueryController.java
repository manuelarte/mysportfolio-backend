package org.manuel.mysportfolio.controllers.query;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.spring.manuelartevalidation.constraints.Exists;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.services.query.PlayersPerformanceQueryService;
import org.manuel.mysportfolio.transformers.PerformanceToPerformanceDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches/{matchId}/performances")
@lombok.AllArgsConstructor
public class PlayersPerformanceQueryController {

  private final PerformanceToPerformanceDtoTransformer performanceToPerformanceDtoTransformer;
  private final PlayersPerformanceQueryService playersPerformanceQueryService;

  @GetMapping(value = "/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PerformanceDto> getPerformanceOf(
      @PathVariable @Exists(Match.class) final ObjectId matchId,
      @PathVariable final String playerId) {
    final var retrieved = playersPerformanceQueryService.findByMatchIdAndPlayerId(matchId, playerId)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("Player %s performance not found in match %s", playerId,
                matchId.toString())));
    return ResponseEntity.ok(performanceToPerformanceDtoTransformer.apply(retrieved));
  }

}
