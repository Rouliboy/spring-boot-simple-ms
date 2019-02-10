package org.jvi.demo.controllers;

import javax.validation.Valid;
import org.jvi.demo.validation.constraints.Site;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/bean", produces = {"application/json"})
@Validated
public class BeanValidationController {

  @GetMapping("/validation")
  public ResponseEntity<String> test(
      @Valid @Site @RequestParam(required = false) final String siteId) {

    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

}
