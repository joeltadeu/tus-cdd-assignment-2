package com.pms.exception;

import com.pms.exception.model.AttributeMessage;
import java.io.Serial;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class BadRequestException extends RuntimeException {
  @Serial private static final long serialVersionUID = 136827568356021732L;

  private final HttpStatus status;

  private List<AttributeMessage> messages;
  private String message;

  public BadRequestException(List<AttributeMessage> messages) {
    this.status = HttpStatus.BAD_REQUEST;
    this.messages = messages;
  }

  public BadRequestException(String message) {
    this.status = HttpStatus.BAD_REQUEST;
    this.message = message;
  }
}
