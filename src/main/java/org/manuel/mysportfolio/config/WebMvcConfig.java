package org.manuel.mysportfolio.config;

import org.manuel.mysportfolio.config.converter.StringToQueryCriteriaConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@lombok.AllArgsConstructor
class WebConfig implements WebMvcConfigurer {

    private final StringToQueryCriteriaConverter stringToQueryCriteriaConverter;

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(stringToQueryCriteriaConverter);
    }

}
