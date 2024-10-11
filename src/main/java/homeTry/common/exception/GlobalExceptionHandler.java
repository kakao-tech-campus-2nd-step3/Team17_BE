package homeTry.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import homeTry.common.exception.dto.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorType errorType = ex.getErrorType();
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException ex) {
        ErrorType errorType = ex.getErrorType();
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
