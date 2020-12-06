package nl.hr.recipefinder.model.httpexception.clienterror;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 409
@ResponseStatus(code = HttpStatus.CONFLICT)
public class HttpConflictError extends RuntimeException {
}
