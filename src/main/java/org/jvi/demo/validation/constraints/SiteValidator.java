package org.jvi.demo.validation.constraints;

import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiteValidator implements ConstraintValidator<Site, String> {

  private static final List<String> sitesValues = Arrays.asList("m0", "m1", "m2");

  @Override
  public void initialize(final Site siteValue) {
    log.info("***************************************");
    log.info("SiteValidator");
  }

  @Override
  public boolean isValid(final String siteId,
      final ConstraintValidatorContext constraintValidatorContext) {

    log.info("isValid");

    // null values are valid
    if (siteId == null) {
      return true;
    }

    return isPositiveInteger(siteId) || sitesValues.contains(siteId);
  }

  private boolean isPositiveInteger(final String siteId) {
    int id = -1;
    try {
      id = Integer.valueOf(siteId);
    } catch (final Exception e) {
    }
    return id >= 0;
  }

}
