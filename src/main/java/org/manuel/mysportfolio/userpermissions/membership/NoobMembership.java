package org.manuel.mysportfolio.userpermissions.membership;

import org.manuel.mysportfolio.userpermissions.CompetitionsRestrictions;
import org.manuel.mysportfolio.userpermissions.MatchRestrictions;
import org.manuel.mysportfolio.userpermissions.TeamsRestrictions;

@lombok.Getter
public class NoobMembership extends AbstractMembership {

  public NoobMembership() {
    this.teamRestrictions = TeamsRestrictions.getNoobMembershipRestrictions();
    this.competitionRestrictions = CompetitionsRestrictions.getNoobMembershipRestrictions();
    this.matchRestrictions = MatchRestrictions.getNoobMembershipRestrictions();
  }

}
