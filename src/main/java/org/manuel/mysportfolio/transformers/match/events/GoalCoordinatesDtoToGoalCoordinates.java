package org.manuel.mysportfolio.transformers.match.events;

import io.github.manuelarte.mysportfolio.model.dtos.match.events.GoalMatchEventDto.PointDto;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

@Component
public class GoalCoordinatesDtoToGoalCoordinates implements Function<PointDto, Point> {

  @Override
  public Point apply(@Nullable final PointDto pointDto) {
    if (pointDto != null) {
      return new Point(pointDto.getX().doubleValue(), pointDto.getY().doubleValue());
    }
    return null;
  }
}
