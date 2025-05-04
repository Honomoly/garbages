package com.honomoly.garbages.config;

import java.time.Instant;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.honomoly.garbages.config.serializer.ByteToIntSerializer;
import com.honomoly.garbages.config.serializer.InstantToDatetimeSerializer;

@Configuration
public class BeanContainer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.timeZone(TimeZone.getDefault());

            // Serializer 등록
            SimpleModule module = new SimpleModule();
            module.addSerializer(Byte.class, new ByteToIntSerializer());
            module.addSerializer(Instant.class, new InstantToDatetimeSerializer());

            builder.modules(module);
        };
    }

}
