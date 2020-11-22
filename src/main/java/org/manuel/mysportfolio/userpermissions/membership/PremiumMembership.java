package org.manuel.mysportfolio.userpermissions.membership;

import org.manuel.mysportfolio.userpermissions.CompetitionsRestrictions;
import org.manuel.mysportfolio.userpermissions.MatchRestrictions;
import org.manuel.mysportfolio.userpermissions.TeamsRestrictions;

@lombok.Getter
public class PremiumMembership extends AbstractMembership {

  public PremiumMembership() {
    this.teamRestrictions = TeamsRestrictions.getPremiumMembershipRestrictions();
    this.competitionRestrictions = CompetitionsRestrictions.getPremiumMembershipRestrictions();
    this.matchRestrictions = MatchRestrictions.getPremiumMembershipRestrictions();
  }

}
