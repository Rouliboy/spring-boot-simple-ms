package org.jvi.demo.repositories;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jvi.demo.model.Evenement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EvenementRepository {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Evenement> retrieveEvenements() {

    final Map<String, Object> map = new HashMap<>();
    map.put("clientId", 2);
    map.put("toot", 2);

    final String query =
        "SELECT nom, numero, client_id, debut from common_evenement where client_id=:clientId";
    return namedParameterJdbcTemplate.query(query, map,
        new BeanPropertyRowMapper<Evenement>(Evenement.class));
  }

  public List<Evenement> retrieveEvenementsWithCallback() {
    final Map<String, Object> map = new HashMap<>();
    map.put("clientId", 2);
    map.put("toot", 2);

    final String query =
        "SELECT nom, numero, client_id, debut from common_evenement where client_id=:clientId";

    return namedParameterJdbcTemplate.execute(query, map,
        new PreparedStatementCallback<List<Evenement>>() {

          @Override
          public List<Evenement> doInPreparedStatement(final PreparedStatement statement)
              throws SQLException, DataAccessException {
            // TODO Auto-generated method stub

            final List<Evenement> result = new ArrayList<>();
            ResultSet resultSet = null;
            boolean isResultSet = statement.execute();

            int count = 0;
            while (true) {
              if (isResultSet) {
                resultSet = statement.getResultSet();

                final BeanPropertyRowMapper<Evenement> mapper =
                    new BeanPropertyRowMapper<Evenement>(Evenement.class);

                int rowNumber = 0;
                while (resultSet.next()) {
                  final Evenement fl = mapper.mapRow(resultSet, ++rowNumber);
                  result.add(fl);
                }
                resultSet.close();
              } else {
                if (statement.getUpdateCount() == -1) {
                  break;
                }
                log.debug("Result {} is just a count: {}", count, statement.getUpdateCount());
              }
              count++;
              isResultSet = statement.getMoreResults();
            }
            return result;
          }

        });
  }

  private Set<ResultSet> executeProcedure(final String sql) {
    return jdbcTemplate.execute(new CallableStatementCreator() {
      @Override
      public CallableStatement createCallableStatement(final Connection con) throws SQLException {
        return con.prepareCall(sql);
      }
    }, new CallableStatementCallback<Set<ResultSet>>() {
      @Override
      public Set<ResultSet> doInCallableStatement(final CallableStatement cs) throws SQLException {
        final Set<ResultSet> results = new HashSet<>();

        boolean resultsAvailable = cs.execute();

        while (resultsAvailable) {
          results.add(cs.getResultSet());
          resultsAvailable = cs.getMoreResults();
        }
        return results;
      }
    });
  }
}
