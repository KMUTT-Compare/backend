package sit.int371.capstoneproject.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse{
    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
