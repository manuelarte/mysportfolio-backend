package org.manuel.mysportfolio.controllers.command;

import lombok.AllArgsConstructor;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.services.command.PlayersPerformanceCommandService;
import org.manuel.mysportfolio.transformers.PerformanceDtoToPerformanceTransformer;
import org.manuel.mysportfolio.transformers.PerformanceToPerformanceDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches/{matchId}/performances")
@AllArgsConstructor
public class PerformanceCommandController {

    private final PerformanceDtoToPerformanceTransformer performanceDtoToPerformanceTransformer;
    private final PerformanceToPerformanceDtoTransformer performanceToPerformanceDtoTransformer;
    private final PlayersPerformanceCommandService playersPerformanceCommandService;

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PerformanceDto> updatePerformance(
            @PathVariable final String matchId,
            @RequestBody final PerformanceDto performanceDto) {
        final var updated = playersPerformanceCommandService.updatePerformance(matchId, "manueldoncelmartos", performanceDtoToPerformanceTransformer.apply(performanceDto));
        return ResponseEntity.ok(performanceToPerformanceDtoTransformer.apply(updated));
    }

}
