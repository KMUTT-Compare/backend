package sit.int371.capstoneproject.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponseUtil {
    public static sit.int371.capstoneproject.exceptions.ErrorResponse createErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        return new ErrorResponse(
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), // timestamp
                status.value(), // HTTP status code
                status.getReasonPhrase(), // status reason (e.g., "Not Found")
                message,
                request.getRequestURI() // Request path
        );
    }
}
