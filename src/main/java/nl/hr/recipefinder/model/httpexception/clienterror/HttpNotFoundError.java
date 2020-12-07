package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 404
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HttpNotFoundError extends RuntimeException {
}
