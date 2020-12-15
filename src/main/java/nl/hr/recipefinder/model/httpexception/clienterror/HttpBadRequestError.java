package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 400
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HttpBadRequestError extends RuntimeException {
  public HttpBadRequestError(){ super();}
  public HttpBadRequestError(String message){ super(message);}
  public HttpBadRequestError(Throwable cause){ super(cause);}
  public HttpBadRequestError( String message, Throwable cause){ super(message, cause);}
}
