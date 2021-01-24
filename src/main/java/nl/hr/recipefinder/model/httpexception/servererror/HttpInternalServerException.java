package nl.hr.recipefinder.model.httpexception.servererror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 500
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpInternalServerException extends RuntimeException {

  public HttpInternalServerException(){ super();}
  public HttpInternalServerException(String message){ super(message);}
  public HttpInternalServerException(Throwable cause){ super(cause);}
  public HttpInternalServerException(String message, Throwable cause){ super(message, cause);}

}
