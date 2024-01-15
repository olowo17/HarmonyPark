package com.harmonypark.harmonypark.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

import static utils.GeneralConstants.DATETIME_FORMAT;
import static utils.GeneralConstants.LOCAL_DATE_TIME_SERIALIZER;
@Configuration
public class JacksonConfig {

    @Bean("HarmonyParkObjectMapper")
    public ObjectMapper objectMapper(@Qualifier("HarmonyParkTimeModule") JavaTimeModule javaTimeModule) {
        return JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .defaultDateFormat(new SimpleDateFormat(DATETIME_FORMAT))
                .addModule(javaTimeModule)
                .build();
    }

    @Bean("HarmonyParkTimeModule")
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATE_TIME_SERIALIZER);
        return module;
    }
}
