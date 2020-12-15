package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 409
@ResponseStatus(code = HttpStatus.CONFLICT)
public class HttpConflictError extends RuntimeException {
  public HttpConflictError(){ super();}
  public HttpConflictError(String message){ super(message);}
  public HttpConflictError(Throwable cause){ super(cause);}
  public HttpConflictError( String message, Throwable cause){ super(message, cause);}
}
