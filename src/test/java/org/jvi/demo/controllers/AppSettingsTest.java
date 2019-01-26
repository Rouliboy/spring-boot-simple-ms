package org.jvi.demo.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppSettingsTest {

  @Autowired
  private AppSettings appSettings;

  @Test
  public void test() {
    System.out.println(appSettings.getProperty(null));
  }

  private int computePage(final int pageId, final int nbElementsByPages) {

    return 1;

  }

}
