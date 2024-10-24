package homeTry.common.exception;

import homeTry.common.exception.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTRAINT_VIOLATION_ERROR_CODE = "Field400_001";
    private static final String ILLEGAL_ARGUMENT_ERROR_CODE = "Field400_002";


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorType errorType = CommonErrorType.METHOD_ARGUMENT_NOT_VALID_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorType errorType = CommonErrorType.MISSING_PATH_VARIABLE_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorType errorType = CommonErrorType.MISSING_REQUEST_PARAM_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ErrorType errorType = CommonErrorType.NO_SUCH_ARGUMENT_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(errorType.getErrorCode(), errorType.getMessage());
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(ILLEGAL_ARGUMENT_ERROR_CODE, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse(CONSTRAINT_VIOLATION_ERROR_CODE, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
