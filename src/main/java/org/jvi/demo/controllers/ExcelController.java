package org.jvi.demo.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.jvi.demo.model.TestPojoWithColumns;
import org.jvi.demo.model.pagination.Pagination;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nexity.wgl.excel.ExcelCreator;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/excel", produces = {"application/json"})
@Validated
public class ExcelController {

  @GetMapping("/download")
  public void test(final HttpServletResponse response) throws IOException {

    final List<TestPojoWithColumns> values = new ArrayList<>();
    values.add(new TestPojoWithColumns("name1", "surname1", 35, Instant.now(),
        Date.from(Instant.now()), Date.from(Instant.now())));

    final ExcelCreator wbc = new ExcelCreator();

    final ByteArrayInputStream in = wbc.createFrom("Test", values, TestPojoWithColumns.class);

    // xls file
    response.addHeader("Content-disposition", "attachment;filename=sample.xls");
    response.setContentType("application/octet-stream");

    // Copy the stream to the response's output stream.
    IOUtils.copy(in, response.getOutputStream());
    response.flushBuffer();
  }

  @GetMapping("/pagination")
  public ResponseEntity<String> pagination(@Valid final Pagination pagination) {

    log.info("pagination=" + pagination);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

}
