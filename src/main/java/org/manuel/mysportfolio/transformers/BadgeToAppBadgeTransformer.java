package org.manuel.mysportfolio.transformers;

import java.util.function.Function;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.dtos.AppBadgeDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BadgeToAppBadgeTransformer implements Function<Badge, AppBadgeDto> {

	private final MessageSource badgesMessageSource;

	public BadgeToAppBadgeTransformer(@Qualifier("badgesMessageSource") final MessageSource badgesMessageSource) {
		this.badgesMessageSource = badgesMessageSource;
	}

	@Override
	public AppBadgeDto apply(final Badge badge) {
		if (badge == null) {
			return null;
		}
		return AppBadgeDto.builder()
				.badge(badge)
				.displayName(getDisplayName(badge))
			  .imageUrl(badge.getImageUrl())
			  .points(badge.getPoints())
				.build();
	}

	private String getDisplayName(final Badge badge) {
		return badgesMessageSource.getMessage(badge.name(), null, badge.name(), LocaleContextHolder.getLocale());
	}

}
