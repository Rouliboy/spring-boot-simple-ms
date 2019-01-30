package org.jvi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Config {

  @Bean
  public ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder mapperBuilder) {
    // @formatter:off
    return mapperBuilder
        .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build()
        .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
        .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    // @formatter:on
  }
}
