package org.manuel.mysportfolio.controllers.command;

import javax.validation.groups.Default;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.services.command.CompetitionCommandService;
import org.manuel.mysportfolio.transformers.CompetitionDtoToCompetitionTransformer;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.manuel.mysportfolio.transformers.PartialCompetitionDtoToCompetitionTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/competitions")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class CompetitionCommandController {

  private final CompetitionCommandService competitionCommandService;
  private final CompetitionDtoToCompetitionTransformer competitionDtoToCompetitionTransformer;
  private final CompetitionToCompetitionDtoTransformer competitionToCompetitionDtoTransformer;
  private final PartialCompetitionDtoToCompetitionTransformer partialCompetitionDtoToCompetitionTransformer;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CompetitionDto> saveCompetition(@Validated({Default.class,
      NewEntity.class}) @RequestBody final CompetitionDto competitionDto) {
    final var saved = competitionCommandService
        .save(competitionDtoToCompetitionTransformer.apply(competitionDto));
    final var location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(saved.getId()).toUri();
    log.info("Competition with id {}, created by {} saved", saved.getId(), saved.getCreatedBy());
    return ResponseEntity.created(location)
        .body(competitionToCompetitionDtoTransformer.apply(saved));

  }

  @PatchMapping(value = "/{competitionId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CompetitionDto> partialUpdateCompetition(
      @PathVariable final String competitionId,
      @Validated({Default.class,
          PartialUpdateEntity.class}) @RequestBody final CompetitionDto competitionDto) {
    final var updated = partialCompetitionDtoToCompetitionTransformer
        .apply(competitionId, competitionDto);
    return ResponseEntity.ok(competitionToCompetitionDtoTransformer
        .apply(competitionCommandService.update(updated)));


  }

}
