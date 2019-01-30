package org.jvi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.nexity.wgl.lib.logging.config.EnableServiceLogging;
import com.nexity.wgl.lib.security.config.EnableServiceSecurity;
import com.nexity.wgl.lib.utilctrl.configuration.EnableUtilControl;
import com.nexity.wgl.lib.utils.cors.EnableCORS;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableServiceSecurity
@EnableServiceLogging
@EnableCORS
@EnableUtilControl
public class SimpleApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SimpleApplication.class, args);
  }
}
