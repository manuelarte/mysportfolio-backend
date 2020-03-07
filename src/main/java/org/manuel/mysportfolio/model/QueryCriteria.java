package org.manuel.mysportfolio.model;

import java.util.List;
import org.springframework.data.util.Pair;

@lombok.AllArgsConstructor
@lombok.Data
@lombok.EqualsAndHashCode
@lombok.Builder(toBuilder = true)
public class QueryCriteria {

  private final SearchCriterion first;
  @lombok.Singular("addAnotherCriteria")
  private final List<Pair<QueryOption, SearchCriterion>> rest;

  public enum QueryOption {AND, OR}

}
