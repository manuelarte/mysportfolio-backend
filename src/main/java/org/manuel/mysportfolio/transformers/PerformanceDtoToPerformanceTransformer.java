package org.manuel.mysportfolio.transformers;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.springframework.stereotype.Component;

@Component
public class PerformanceDtoToPerformanceTransformer implements
    Function<PerformanceDto, Performance> {

    @Override
    public Performance apply(PerformanceDto performanceDto) {
        return new Performance(performanceDto.getRate(), performanceDto.getNotes());
    }

}
