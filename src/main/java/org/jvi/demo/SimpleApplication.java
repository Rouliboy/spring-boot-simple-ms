package org.jvi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nexity.wgl.lib.cors.config.EnableCORS;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableCORS
public class SimpleApplication {

	public static void main(final String[] args) {

		log.info("START JVI ");
		SpringApplication.run(SimpleApplication.class, args);
	}
}
