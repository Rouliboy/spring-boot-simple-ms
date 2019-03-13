package org.jvi.demo.annotation.processor;

import com.nexity.wgl.lib.utils.annotation.processor.Immutable;

@Immutable
public class MutableClass {
  private String name;

  public MutableClass(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
