package org.manuel.mysportfolio.controllers.query;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.match.events.MatchEventDto;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.manuel.mysportfolio.transformers.match.events.MatchEventToMatchEventDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches/{matchId}/events")
@lombok.AllArgsConstructor
public class MatchEventQueryController {

  private final MatchQueryService matchQueryService;
  private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;
  private final MatchEventToMatchEventDtoTransformer matchEventToMatchEventDtoTransformer;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MatchEventDto<MatchEvent>>> findAll(@PathVariable final ObjectId matchId) {
    return ResponseEntity.ok(matchToMatchDtoTransformer.apply(matchQueryService.findOne(matchId)
        .orElseThrow(() -> new EntityNotFoundException(Match.class, matchId.toString())))
        .getEvents());
  }

  @GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MatchEventDto<MatchEvent>> findOne(@PathVariable final ObjectId matchId,
      @PathVariable final ObjectId eventId) {
    // TODO
    final var matchEvents = matchQueryService.findOne(matchId).orElseThrow(() ->
        new EntityNotFoundException(String.format("Match with id %s not found", matchId)))
        .getEvents();
    final Optional<MatchEvent> event = matchEvents.stream().filter(predicate(eventId)).findFirst();
    final MatchEvent matchEvent = event.orElseThrow(
        () -> new EntityNotFoundException(String.format("Event with id %s not found", eventId)));
    return ResponseEntity.ok(matchEventToMatchEventDtoTransformer.apply(matchEvent));
  }

  private Predicate<MatchEvent> predicate(final ObjectId eventId) {
    return e -> e.getId().equals(eventId);
  }

}
