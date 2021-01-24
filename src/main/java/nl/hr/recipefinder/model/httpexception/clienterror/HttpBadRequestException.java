package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 400
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HttpBadRequestException extends RuntimeException {
  public HttpBadRequestException(){ super();}
  public HttpBadRequestException(String message){ super(message);}
  public HttpBadRequestException(Throwable cause){ super(cause);}
  public HttpBadRequestException(String message, Throwable cause){ super(message, cause);}
}
