package org.manuel.mysportfolio.transformers.match.events;

import io.github.manuelarte.mysportfolio.model.dtos.match.events.GoalMatchEventDto.PointDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

@Component
public class GoalCoordinatesToGoalCoordinatesDto implements Function<Point, PointDto> {

  @Override
  public PointDto apply(@Nullable final Point point) {
    if (point != null) {
      return new PointDto(
          BigDecimal.valueOf(point.getX()).setScale(2, RoundingMode.CEILING),
          BigDecimal.valueOf(point.getY()).setScale(2, RoundingMode.CEILING));
    }
    return null;
  }
}
