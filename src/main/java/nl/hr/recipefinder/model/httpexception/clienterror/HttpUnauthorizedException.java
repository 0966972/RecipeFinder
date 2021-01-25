package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 401
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class HttpUnauthorizedException extends RuntimeException {
  public HttpUnauthorizedException() {
    super();
  }

  public HttpUnauthorizedException(String message) {
    super(message);
  }

  public HttpUnauthorizedException(Throwable cause) {
    super(cause);
  }

  public HttpUnauthorizedException(String message, Throwable cause) {
    super(message, cause);
  }
}
