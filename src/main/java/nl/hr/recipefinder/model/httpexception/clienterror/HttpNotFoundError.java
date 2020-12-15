package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 404
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HttpNotFoundError extends RuntimeException {
  public HttpNotFoundError(){ super();}
  public HttpNotFoundError(String message){ super(message);}
  public HttpNotFoundError(Throwable cause){ super(cause);}
  public HttpNotFoundError( String message, Throwable cause){ super(message, cause);}
}
