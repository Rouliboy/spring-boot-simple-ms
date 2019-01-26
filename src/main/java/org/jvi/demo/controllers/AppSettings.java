package org.jvi.demo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AppSettings {

  private static final String APP_SETTINGS_PROPERTIES_FILE = "/com/nexity/app-settings.properties";

  private final Properties appSettingsProperties = new Properties();

  @PostConstruct
  public void initialize() {

    try (
        final InputStream inStream = getClass().getResourceAsStream(APP_SETTINGS_PROPERTIES_FILE)) {

      appSettingsProperties.load(inStream);

    } catch (final IOException e) {
      throw new IllegalStateException("Error reading file ", e);
    }

  }

  public String getProperty(final String propertyName) {

    if (!StringUtils.hasText(propertyName)) {
      return null;
    }
    return appSettingsProperties.getProperty(propertyName);
  }
}
