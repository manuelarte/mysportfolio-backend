package org.manuel.mysportfolio.userpermissions.membership;

import java.util.Set;
import org.manuel.mysportfolio.userpermissions.UserRestrictionInterval;

@lombok.Getter
public abstract class AbstractMembership {

  protected Set<UserRestrictionInterval> teamRestrictions;
  protected Set<UserRestrictionInterval> competitionRestrictions;
  protected Set<UserRestrictionInterval> matchRestrictions;



}
