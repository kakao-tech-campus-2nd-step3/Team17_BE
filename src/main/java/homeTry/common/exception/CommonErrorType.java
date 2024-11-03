package homeTry.common.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorType implements ErrorType {
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION("Common400_001", HttpStatus.BAD_REQUEST, "요청값에 대한 유효성 검사를 통과하지 못했습니다. 유효한 데이터를 입력해주세요(RequestBody)"),
    ILLEGAL_ARGUMENT_EXCEPTION("Common400_002", HttpStatus.BAD_REQUEST, "IllegalArgumentException 발생"),
    NO_SUCH_ARGUMENT_EXCEPTION("Common404_003", HttpStatus.NOT_FOUND, "요구하신 요청에 맞는 리소스를 찾을 수 없습니다."),
    MISSING_PATH_VARIABLE_EXCEPTION("Common400_004", HttpStatus.BAD_REQUEST, "PathVariable이 누락됐습니다."),
    MISSING_REQUEST_PARAM_EXCEPTION("Common400_005", HttpStatus.BAD_REQUEST, "RequestParameter가 누락됐습니다."),
    CONSTRAINT_VIOLATION_EXCEPTION("Common400_006", HttpStatus.BAD_REQUEST, "ConstraintViolationException 발생"),
    HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION("Common400_007", HttpStatus.BAD_REQUEST, "HttpRequestMethodNotSupportedException 발생"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION("Common400_008", HttpStatus.BAD_REQUEST, "날짜를 LocalDate타입으로 변환이 실패되었습니다. 올바른 날짜를 입력해주세요 ex)20240715"),
    HANDLER_METHOD_VALIDATION_EXCEPTION("Common400_009", HttpStatus.BAD_REQUEST, "요청값에 대한 유효성 검사에 실패하였습니다(RequestParam, PathVariable");

    private String errorCode;
    private HttpStatus httpStatus;
    private String message;

    CommonErrorType(String errorCode, HttpStatus httpStatus, String message) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
