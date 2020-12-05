package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.Place;
import io.github.manuelarte.mysportfolio.model.dtos.PlaceDto;
import io.github.manuelarte.mysportfolio.model.dtos.PlaceDto.LatitudeAndLongitudeDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlaceToPlaceDtoTransformer implements Function<Place, PlaceDto> {

  @Override
  public PlaceDto apply(final Place place) {
    if (place != null) {
      return PlaceDto.builder()
          .formattedAddress(place.getFormattedAddress())
          .latLng(LatitudeAndLongitudeDto.builder()
              .latitude(place.getLatLng().getLatitude())
              .longitude(place.getLatLng().getLongitude())
              .build())
          .build();
    }
    return null;
  }
}
