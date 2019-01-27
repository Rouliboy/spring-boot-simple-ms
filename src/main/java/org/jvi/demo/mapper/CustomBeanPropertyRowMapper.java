package org.jvi.demo.mapper;

import java.lang.reflect.Field;
import javax.persistence.Column;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

  public CustomBeanPropertyRowMapper() {
    super();
  }

  /**
   * Create a new {@code BeanPropertyRowMapper}, accepting unpopulated properties in the target
   * bean.
   * <p>
   * Consider using the {@link #newInstance} factory method instead, which allows for specifying the
   * mapped type once only.
   *
   * @param mappedClass the class that each row should be mapped to
   */
  public CustomBeanPropertyRowMapper(final Class<T> mappedClass) {
    super(mappedClass);
  }

  /**
   * Create a new {@code BeanPropertyRowMapper}.
   *
   * @param mappedClass the class that each row should be mapped to
   * @param checkFullyPopulated whether we're strictly validating that all bean properties have been
   *        mapped from corresponding database fields
   */
  public CustomBeanPropertyRowMapper(final Class<T> mappedClass,
      final boolean checkFullyPopulated) {
    super(mappedClass, checkFullyPopulated);
  }

  @Override
  protected String underscoreName(final String name) {

    log.info("look");
    final Column annotation;
    final String columnName;
    Field declaredField = null;

    try {
      declaredField = getMappedClass().getDeclaredField(name);
    } catch (NoSuchFieldException | SecurityException e) {
      log.warn("Ups, field «{}» not found in «{}».", name, getMappedClass());
    }

    if (declaredField == null || (annotation = declaredField.getAnnotation(Column.class)) == null
        || StringUtils.isEmpty(columnName = annotation.name())) {
      return super.underscoreName(name);
    }

    return StringUtils.lowerCase(columnName);
  }
}
