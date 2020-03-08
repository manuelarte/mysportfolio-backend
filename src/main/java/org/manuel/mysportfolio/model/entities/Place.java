package org.manuel.mysportfolio.model.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Place.PlaceBuilder.class)
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class Place {

  private String formattedAddress;
  private LatitudeAndLongitude latLng;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlaceBuilder {

  }

  @JsonDeserialize(builder = LatitudeAndLongitude.LatitudeAndLongitudeBuilder.class)
  @lombok.AllArgsConstructor
  @lombok.NoArgsConstructor
  @lombok.Data
  @lombok.Builder(toBuilder = true)
  public static class LatitudeAndLongitude {

    private double latitude;
    private double longitude;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LatitudeAndLongitudeBuilder {

    }
  }
}
