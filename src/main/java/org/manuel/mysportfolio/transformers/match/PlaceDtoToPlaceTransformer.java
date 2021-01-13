package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.Place;
import io.github.manuelarte.mysportfolio.model.Place.LatitudeAndLongitude;
import io.github.manuelarte.mysportfolio.model.dtos.PlaceDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlaceDtoToPlaceTransformer implements Function<PlaceDto, Place> {

  @Override
  public Place apply(PlaceDto placeDto) {
    if (placeDto != null) {
      final var latitudeLongitudeDto = placeDto.getLatLng();
      return new Place(placeDto.getFormattedAddress(),
          new LatitudeAndLongitude(latitudeLongitudeDto.getLatitude(), latitudeLongitudeDto.getLongitude()));
    }
    return null;
  }
}
