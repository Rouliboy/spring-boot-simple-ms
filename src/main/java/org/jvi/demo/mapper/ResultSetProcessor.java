package org.jvi.demo.mapper;

import java.sql.ResultSet;
import java.util.Collection;

public interface ResultSetProcessor<T> {

  Class<T> target();

  boolean canHandle(final ResultSet resultSet);

  Collection<T> map(final ResultSet resultSet, Class<?> class1);

}
