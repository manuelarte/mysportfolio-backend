package org.manuel.mysportfolio.model.entities.match.events;

import org.manuel.mysportfolio.model.entities.TeamOption;

public interface TeamMatchEvent extends MatchEvent{

    TeamOption getTeam();

    void setTeam(TeamOption team);

}
