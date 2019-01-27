package org.jvi.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultSetMapper {

  // ----------------- public methods ------------------

  /**
   * Map a {@link ResultSet} to a list of objects. <b>Beware that ResultSet is not closed in this
   * method</b>. Closing must be managed by caller.
   *
   * @param resultSet The result set to map
   * @param outputModel The mapped model
   * @return A list of model
   * @throws SQLException
   *
   */
  public <T> List<T> map(final ResultSet resultSet, final Class<T> outputModel)
      throws SQLException {

    Validate.notNull(resultSet, "Result set can not be null");
    Validate.notNull(outputModel, "Output model can not be null");

    log.info("Call map of resultSetMapper");

    final List<T> outputList = new ArrayList<>();

    final CustomBeanPropertyRowMapper<T> rowMapper =
        new CustomBeanPropertyRowMapper<T>(outputModel);

    int rowNumber = 0;
    while (resultSet.next()) {
      outputList.add(rowMapper.mapRow(resultSet, ++rowNumber));
    }

    return outputList;
  }

}
