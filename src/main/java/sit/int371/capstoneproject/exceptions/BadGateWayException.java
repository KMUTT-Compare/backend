package sit.int371.capstoneproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class BadGateWayException extends RuntimeException{
    public BadGateWayException(String message, Throwable cause){
        super(message, cause);
    }
}
