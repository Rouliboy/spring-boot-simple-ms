package org.jvi.demo.mapper;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResultSetProcessorHandler {

  // @Autowired
  private Collection<ResultSetProcessor<?>> resultSetProcessors;

  @PostConstruct
  public void init() {
    // log.info("NB processors found : {}", resultSetProcessors.size());
  }

  public void map(final ResultSet resultSet, final List<List<?>> allLists) {

    for (final List<?> currentList : allLists) {
      for (final ResultSetProcessor<?> processor : resultSetProcessors) {
        // if (processor.canHandle(resultSet)) {
        // currentList.addAll(processor.map(resultSet, processor.target()));
        // }
      }
    }

  }
}
