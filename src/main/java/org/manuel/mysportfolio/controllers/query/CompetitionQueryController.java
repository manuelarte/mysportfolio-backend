package org.manuel.mysportfolio.controllers.query;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      @PageableDefault final Pageable pageable,
      @RequestParam(value = "matchDate", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Optional<LocalDate> matchDate) {
    if (matchDate.isPresent()) {
      final YearMonth toYearMonth = YearMonth.from(matchDate.get());
      final Criteria criteria = new Criteria().orOperator(Criteria.where("to").gte(toYearMonth), Criteria.where("to").is(null));
      return ResponseEntity.ok(competitionQueryService.findAllByQueryAndCreatedBy(pageable, getUserId(), new Query(criteria))
          .map(competitionToCompetitionDtoTransformer));
    } else {
      return ResponseEntity.ok(competitionQueryService.findAllCreatedBy(pageable, getUserId())
          .map(competitionToCompetitionDtoTransformer));
    }
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
