package org.manuel.mysportfolio.model.dtos;

import io.github.manuelarte.mysportfolio.model.Place;
import io.github.manuelarte.mysportfolio.model.Place.LatitudeAndLongitude;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class PlaceDto {

  private String formattedAddress;
  private LatitudeAndLongitudeDto latLng;

  public Place toPlace() {
    return new Place(formattedAddress, latLng.toLatitudeAndLongitude());
  }

  @lombok.AllArgsConstructor
  @lombok.NoArgsConstructor
  @lombok.Data
  @lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
  public static class LatitudeAndLongitudeDto {

    private double latitude;
    private double longitude;

    LatitudeAndLongitude toLatitudeAndLongitude() {
      return new LatitudeAndLongitude(latitude, longitude);
    }

  }
}
