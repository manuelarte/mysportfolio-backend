package org.manuel.mysportfolio.model.entities.badges;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.BaseEntity;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "badges")
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
@lombok.Builder(toBuilder = true)
public class UserBadges  extends BaseEntity {

	public UserBadges(final ObjectId id, final Long version, final String userId, final Set<Badge> badges) {
		super(id, version);
		this.userId = userId;
		this.badges = badges;
	}

	@NotNull
	@Indexed(unique = true)
	private String userId;

	private Set<Badge> badges;

}
