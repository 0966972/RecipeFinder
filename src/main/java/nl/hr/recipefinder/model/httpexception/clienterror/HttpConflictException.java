package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 409
@ResponseStatus(code = HttpStatus.CONFLICT)
public class HttpConflictException extends RuntimeException {
  public HttpConflictException() {
    super();
  }

  public HttpConflictException(String message) {
    super(message);
  }

  public HttpConflictException(Throwable cause) {
    super(cause);
  }

  public HttpConflictException(String message, Throwable cause) {
    super(message, cause);
  }
}
