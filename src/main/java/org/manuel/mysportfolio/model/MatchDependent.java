package org.manuel.mysportfolio.model;

import org.bson.types.ObjectId;

public interface MatchDependent {

  ObjectId getMatchId();

  @SuppressWarnings("unused")
  void setMatchId(ObjectId matchId);

}
