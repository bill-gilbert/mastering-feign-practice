package com.example.caller.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CalleeServiceConfiguration {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    private final ObjectMapper objectMapper;


    @Bean
    public RequestInterceptor authInterceptor() {
        return template ->
                template.header("Authorization", "Bearer ");
    }

    @Bean
    public ResponseEntityDecoder getResponseEntityDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(getObjectFactory()));
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }

    @Bean
    public SpringEncoder getSpringEncoder() {
        return new SpringEncoder(getObjectFactory());
    }

    private ObjectFactory<HttpMessageConverters> getObjectFactory() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        jacksonConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));
        return () -> new HttpMessageConverters(jacksonConverter);
    }
}
