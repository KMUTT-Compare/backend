package sit.int371.capstoneproject.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 400 Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    // 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = ErrorResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    // 409 Conflict
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handlerConflictException(ConflictException ex, HttpServletRequest request){
        ErrorResponse errorResponse = ErrorResponseUtil.createErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    // 500 Internal server
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handlerInternalException(InternalServerException ex, HttpServletRequest request){
        ErrorResponse errorResponse = ErrorResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // 502 Bad Gateway
    @ExceptionHandler(BadGateWayException.class)
    public ResponseEntity<ErrorResponse> handlerBadGateWayException(BadGateWayException ex, HttpServletRequest request){
        ErrorResponse errorResponse = ErrorResponseUtil.createErrorResponse(HttpStatus.BAD_GATEWAY, ex.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
    }
}