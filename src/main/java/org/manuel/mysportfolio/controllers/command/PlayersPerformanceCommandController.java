package org.manuel.mysportfolio.controllers.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.services.command.PlayersPerformanceCommandService;
import org.manuel.mysportfolio.transformers.PerformanceDtoToPerformanceTransformer;
import org.manuel.mysportfolio.transformers.PerformanceToPerformanceDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/matches/{matchId}/performances")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class PlayersPerformanceCommandController {

    private final PerformanceDtoToPerformanceTransformer performanceDtoToPerformanceTransformer;
    private final PerformanceToPerformanceDtoTransformer performanceToPerformanceDtoTransformer;
    private final PlayersPerformanceCommandService playersPerformanceCommandService;
    private final UserIdProvider userIdProvider;

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PerformanceDto> updatePerformance(
            @PathVariable final ObjectId matchId,
            @RequestBody final PerformanceDto performanceDto) {
        final var updated = playersPerformanceCommandService.updatePerformance(matchId, getUserId(), performanceDtoToPerformanceTransformer.apply(performanceDto));
        log.info("Players performance for player {} patched for Match id {}", getUserId(), matchId);
        return ResponseEntity.ok(performanceToPerformanceDtoTransformer.apply(updated));
    }

    private String getUserId() {
        return userIdProvider.getUserId();
    }

}