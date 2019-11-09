package org.manuel.mysportfolio.services;

import org.apache.commons.lang3.time.DateUtils;
import org.manuel.mysportfolio.model.Sport;
import org.springframework.stereotype.Service;
import pl.jsolve.typeconverter.Converter;
import pl.jsolve.typeconverter.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class TypeConversionService {

    public TypeConversionService() {
        TypeConverter.registerConverter(String.class, Instant.class, instantConversion());
        TypeConverter.registerConverter(String.class, Sport.class, stringToSportConversion());
    }

    public <S, T> T convert(final S source, final Class<T> target) {
        return TypeConverter.convert(source, target);
    }

    private Converter<String, Instant> instantConversion() {
        return source -> LocalDate.parse(source).atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    private Converter<String, Sport> stringToSportConversion() {
        return source -> Sport.valueOf(source);
    }

    private Converter<String, LocalDate> localDateConversion() {
        return source -> LocalDate.parse(source);
    }
}
