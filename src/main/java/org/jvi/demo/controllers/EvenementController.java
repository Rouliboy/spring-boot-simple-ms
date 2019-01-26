package org.jvi.demo.controllers;

import java.util.List;
import org.jvi.demo.model.Evenement;
import org.jvi.demo.repositories.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/evenements")
public class EvenementController {

  @Autowired
  private EvenementRepository repo;

  @GetMapping
  public List<Evenement> evenements() {
    return repo.retrieveEvenements();

  }

  @GetMapping("/callback")
  public List<Evenement> retrieveEvenementsWithCallback() {
    return repo.retrieveEvenementsWithCallback();

  }

}
