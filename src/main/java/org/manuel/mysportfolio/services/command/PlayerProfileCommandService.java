package org.manuel.mysportfolio.services.command;

import java.time.Year;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;

public interface PlayerProfileCommandService {

  PlayerProfileSportInfo updateForYear(String externalId, Year year,
      PlayerProfileSportInfo playerProfileSportInfo);

}
