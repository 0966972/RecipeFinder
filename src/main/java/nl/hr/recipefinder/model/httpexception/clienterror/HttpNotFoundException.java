package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 404
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HttpNotFoundException extends RuntimeException {
  public HttpNotFoundException(){ super();}
  public HttpNotFoundException(String message){ super(message);}
  public HttpNotFoundException(Throwable cause){ super(cause);}
  public HttpNotFoundException(String message, Throwable cause){ super(message, cause);}
}
