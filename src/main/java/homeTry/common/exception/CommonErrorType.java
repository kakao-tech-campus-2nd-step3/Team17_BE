package homeTry.common.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorType implements ErrorType {
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION("Common400_001", HttpStatus.BAD_REQUEST, "요청값에 대한 유효성 검사를 통과하지 못했습니다. 유효한 데이터를 입력해주세요(RequestBody)"),
    ILLEGAL_ARGUMENT_EXCEPTION("Common400_002", HttpStatus.BAD_REQUEST, "IllegalArgumentException 발생"),
    MISSING_PATH_VARIABLE_EXCEPTION("Common400_004", HttpStatus.BAD_REQUEST, "PathVariable이 누락됐습니다."),
    MISSING_REQUEST_PARAM_EXCEPTION("Common400_005", HttpStatus.BAD_REQUEST, "RequestParameter가 누락됐습니다."),
    CONSTRAINT_VIOLATION_EXCEPTION("Common400_006", HttpStatus.BAD_REQUEST, "ConstraintViolationException 발생"),
    HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION("Common400_007", HttpStatus.BAD_REQUEST, "해당 endPoint로는 요청하신 타입의 Http 메소드를 지원하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION("Common400_008", HttpStatus.BAD_REQUEST, "API에 적힌 쿼리 파라미터에 맞는 형식의 데이터인지, 데이터가 누락되지 않았는지 확인해주세요"),
    HANDLER_METHOD_VALIDATION_EXCEPTION("Common400_009", HttpStatus.BAD_REQUEST, "요청값에 대한 유효성 검사에 실패하였습니다(RequestParam, PathVariable"),

    NO_SUCH_ARGUMENT_EXCEPTION("Common404_003", HttpStatus.NOT_FOUND, "요구하신 요청에 맞는 리소스를 찾을 수 없습니다."),

    INTERNAL_SERVER_EXCEPTION("Common500_001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 예상치 못한 에러가 발생하였습니다. 관리자에게 문의 바랍니다 ㅠㅠ");

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
