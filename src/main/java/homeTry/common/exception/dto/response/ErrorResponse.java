package homeTry.common.exception.dto.response;

public record ErrorResponse(
    String errorCode,
    String message
) {
    
}
