package org.manuel.mysportfolio.userpermissions.membership;

import org.manuel.mysportfolio.userpermissions.CompetitionsRestrictions;
import org.manuel.mysportfolio.userpermissions.MatchRestrictions;
import org.manuel.mysportfolio.userpermissions.TeamsRestrictions;

@lombok.Getter
public class FreeMembership extends AbstractMembership {

  public FreeMembership() {
    this.teamRestrictions = TeamsRestrictions.getFreeMembershipRestrictions();
    this.competitionRestrictions = CompetitionsRestrictions.getFreeMembershipRestrictions();
    this.matchRestrictions = MatchRestrictions.getFreeMembershipRestrictions();
  }

}
