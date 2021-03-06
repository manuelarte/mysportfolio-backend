package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.documents.playersperformance.Performance;
import io.github.manuelarte.mysportfolio.model.dtos.match.PerformanceDto;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PerformanceToPerformanceDtoTransformer implements
    Function<Performance, PerformanceDto> {

  @Override
  public PerformanceDto apply(final Performance performance) {
    return Optional.ofNullable(performance)
        .map(it -> new PerformanceDto(it.getRate(), it.getNotes())).orElse(null);
  }

}
