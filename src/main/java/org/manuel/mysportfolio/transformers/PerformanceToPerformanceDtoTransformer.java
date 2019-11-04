package org.manuel.mysportfolio.transformers;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.springframework.stereotype.Component;

@Component
public class PerformanceToPerformanceDtoTransformer implements Function<Performance, PerformanceDto> {

    @Override
    public PerformanceDto apply(final Performance performance) {
        return performance == null ? null : new PerformanceDto(performance.getRate(), performance.getNotes());
    }

}
