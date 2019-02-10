package org.jvi.demo.controllers;

import javax.validation.Valid;
import org.jvi.demo.mapper.ResultSetProcessorHandler;
import org.jvi.demo.model.LotSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/demo", produces = {"application/json"})
public class DemoControllerSecured {

  @Autowired
  private ResultSetProcessorHandler processorHandler;

  @GetMapping("/get")
  public ResponseEntity<String> test() {

    final HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer monBearer");

    return new ResponseEntity<String>(headers, HttpStatus.OK);
  }

  @GetMapping("/getParam/{myParam}")
  public ResponseEntity<String> testParam(@PathVariable final String myParam,
      @RequestParam(required = false) final String rParam) {

    throw new CustomErrorException();
  }

  @PostMapping("/post")
  public String post(@RequestBody @Valid final LotSearch search) {

    log.info("Enter in post : {}", search);
    return "OK";
  }

  @PostMapping("/postEx")
  public String postEx(@RequestBody @Valid final LotSearch search) {
    log.info("Enter in post : {}", search);
    throw new IllegalArgumentException("Error!!!");
  }

  @PostMapping("/postExCustom")
  public String postExCustom() {
    throw new CustomErrorException();
  }

  // @RequestMapping("/fiches")
  // public Collection<FicheLocation> fiches() {
  //
  // final List<FicheLocation> fls = new ArrayList<>();
  // final List<List<?>> allLists = new ArrayList<>();
  // allLists.add(fls);
  //
  // processorHandler.map(null, allLists);
  // return fls;
  // }

  @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
  public static class CustomErrorException extends RuntimeException {

  }

}
