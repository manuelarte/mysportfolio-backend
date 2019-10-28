package org.manuel.mysportfolio.controllers.command;

import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.services.command.CompetitionCommandService;
import org.manuel.mysportfolio.transformers.CompetitionDtoToCompetitionTransformer;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.manuel.mysportfolio.transformers.PartialCompetitionDtoToCompetitionTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/competitions")
@lombok.AllArgsConstructor
public class CompetitionCommandController {

    private final CompetitionCommandService competitionCommandService;
    private final CompetitionDtoToCompetitionTransformer competitionDtoToCompetitionTransformer;
    private final CompetitionToCompetitionDtoTransformer competitionToCompetitionDtoTransformer;
    private final PartialCompetitionDtoToCompetitionTransformer partialCompetitionDtoToCompetitionTransformer;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompetitionDto> saveCompetition(@Validated({Default.class, NewEntity.class}) @RequestBody final CompetitionDto competitionDto) {
        final var saved = competitionCommandService.save(competitionDtoToCompetitionTransformer.apply(competitionDto));
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(competitionToCompetitionDtoTransformer.apply(saved));

    }

    @PatchMapping(value = "/{competitionId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompetitionDto> partialUpdateCompetition(@PathVariable final String competitionId,
                                                     @Validated(Default.class) @RequestBody final CompetitionDto competitionDto) {
        final var updated = partialCompetitionDtoToCompetitionTransformer.apply(competitionId, competitionDto);
        return ResponseEntity.ok(competitionToCompetitionDtoTransformer.apply(competitionCommandService.save(updated)));


    }

}
