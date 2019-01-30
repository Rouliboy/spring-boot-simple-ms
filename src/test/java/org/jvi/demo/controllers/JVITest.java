package org.jvi.demo.controllers;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JVITest {

  @Autowired
  private AppSettings2 appSettings;

  @Test
  public void test() {
    Integer.parseInt(" 1 ");
  }

}
