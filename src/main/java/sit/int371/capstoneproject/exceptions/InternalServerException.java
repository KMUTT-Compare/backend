package sit.int371.capstoneproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException{
    private HttpStatus status;
    public InternalServerException(HttpStatus status, String message){
        super (message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
