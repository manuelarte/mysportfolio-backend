package org.manuel.mysportfolio.services;

import org.springframework.stereotype.Service;
import pl.jsolve.typeconverter.Converter;
import pl.jsolve.typeconverter.TypeConverter;

import java.time.Instant;

@Service
public class TypeConversionService {

    public TypeConversionService() {
        TypeConverter.registerConverter(String.class, Instant.class, instantConversion());
    }

    public <S, T> T convert(final S source, final Class<T> target) {
        return TypeConverter.convert(source, target);
    }

    private Converter<String, Instant> instantConversion() {
        return source -> Instant.parse(source);
    }
}
