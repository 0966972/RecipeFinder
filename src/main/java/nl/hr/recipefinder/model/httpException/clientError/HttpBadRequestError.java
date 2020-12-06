package nl.hr.recipefinder.model.httpException.clientError;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 400
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HttpBadRequestError extends RuntimeException{
}
