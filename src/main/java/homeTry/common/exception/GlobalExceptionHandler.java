package homeTry.common.exception;

import homeTry.common.exception.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorType errorType = CommonErrorType.HANDLER_METHOD_VALIDATION_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

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
        String message = String.format("%s (누락된 PathVariable: %s)", errorType.getMessage(), ex.getVariableName());
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                message
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
        String message = String.format("%s (누락된 RequestParameter: %s)", errorType.getMessage(), ex.getParameterName());
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                message
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorType errorType = CommonErrorType.HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION;
        String message = String.format("%s (Not allwed Method: %s)", errorType.getMessage(), ex.getMethod());
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                message
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
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorType errorType = CommonErrorType.NO_SUCH_ARGUMENT_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorType.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorType errorType = CommonErrorType.ILLEGAL_ARGUMENT_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(ConstraintViolationException ex) {
        ErrorType errorType = CommonErrorType.CONSTRAINT_VIOLATION_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorType errorType = CommonErrorType.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
