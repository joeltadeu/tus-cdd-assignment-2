package com.pms.exception;

import java.io.Serializable;
import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

  public static <T extends Serializable> Supplier<NotFoundException> notFound(final T entityId) {
    return () -> new NotFoundException("Could not find data identified by " + entityId);
  }
}
