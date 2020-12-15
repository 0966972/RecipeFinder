package nl.hr.recipefinder.model.httpexception.serverError;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 500
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpInternalServerError extends RuntimeException {

  public HttpInternalServerError(){ super();}
  public HttpInternalServerError(String message){ super(message);}
  public HttpInternalServerError(Throwable cause){ super(cause);}
  public HttpInternalServerError( String message, Throwable cause){ super(message, cause);}

}
