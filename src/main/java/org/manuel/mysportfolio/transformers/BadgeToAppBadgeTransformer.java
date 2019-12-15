package org.manuel.mysportfolio.transformers;

import java.util.function.Function;

import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.dtos.AppBadgeDto;
import org.springframework.stereotype.Component;

@Component
public class BadgeToAppBadgeTransformer implements Function<Badge, AppBadgeDto> {

	@Override
	public AppBadgeDto apply(final Badge badge) {
		if (badge == null) {
			return null;
		}
		return AppBadgeDto.builder()
			.badge(badge)
			.imageUrl(badge.getImageUrl())
			.points(badge.getPoints())
			.build();
	}
}
