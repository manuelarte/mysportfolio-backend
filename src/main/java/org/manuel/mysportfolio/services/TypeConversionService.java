package org.manuel.mysportfolio.services;

import org.manuel.mysportfolio.model.Sport;
import org.springframework.stereotype.Service;
import pl.jsolve.typeconverter.Converter;
import pl.jsolve.typeconverter.TypeConverter;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TypeConversionService {

    public TypeConversionService() {
        TypeConverter.registerConverter(String.class, Instant.class, instantConversion());
        TypeConverter.registerConverter(String.class, Sport.class, stringToSportConversion());
    }

    public <S, T> T convert(final S source, final Class<T> target) {
       return TypeConverter.convert(source, target);
    }

    public <S, T> List<T> convertToList(final Iterable<S> source, final Class<T> target) {
        return StreamSupport.stream(source.spliterator(), false).map(it -> TypeConverter.convert(it, target))
                .collect(Collectors.toList());
    }

    private Converter<String, Instant> instantConversion() {
        return source -> LocalDate.parse(source).atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
    }

    private Converter<String, Sport> stringToSportConversion() {
        return Sport::valueOf;
    }

    private Converter<String, LocalDate> localDateConversion() {
        return LocalDate::parse;
    }
}
