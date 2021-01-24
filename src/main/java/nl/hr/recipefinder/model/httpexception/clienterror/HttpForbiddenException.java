package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 403
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class HttpForbiddenException extends RuntimeException {
  public HttpForbiddenException(){ super();}
  public HttpForbiddenException(String message){ super(message);}
  public HttpForbiddenException(Throwable cause){ super(cause);}
  public HttpForbiddenException(String message, Throwable cause){ super(message, cause);}
}
