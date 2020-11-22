package org.manuel.mysportfolio.userpermissions.membership;

import org.manuel.mysportfolio.userpermissions.CompetitionsRestrictions;
import org.manuel.mysportfolio.userpermissions.MatchRestrictions;
import org.manuel.mysportfolio.userpermissions.TeamsRestrictions;

@lombok.Getter
public class AdvanceMembership extends AbstractMembership {

  public AdvanceMembership() {
    this.teamRestrictions = TeamsRestrictions.getAdvanceMembershipRestrictions();
    this.competitionRestrictions = CompetitionsRestrictions.getAdvanceMembershipRestrictions();
    this.matchRestrictions = MatchRestrictions.getAdvanceMembershipRestrictions();
  }

}
