package org.manuel.mysportfolio.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.Place;
import io.github.manuelarte.mysportfolio.model.Place.LatitudeAndLongitude;

@JsonDeserialize(builder = PlaceDto.PlaceDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlaceDto {

  private String formattedAddress;
  private LatitudeAndLongitudeDto latLng;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlaceDtoBuilder {

  }

  public Place toPlace() {
    return new Place(formattedAddress, latLng.toLatitudeAndLongitude());
  }

  @JsonDeserialize(builder = LatitudeAndLongitudeDto.LatitudeAndLongitudeDtoBuilder.class)
  @lombok.AllArgsConstructor
  @lombok.NoArgsConstructor
  @lombok.Data
  @lombok.Builder(toBuilder = true)
  public static class LatitudeAndLongitudeDto {

    private double latitude;
    private double longitude;

    LatitudeAndLongitude toLatitudeAndLongitude() {
      return new LatitudeAndLongitude(latitude, longitude);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LatitudeAndLongitudeDtoBuilder {

    }
  }
}
