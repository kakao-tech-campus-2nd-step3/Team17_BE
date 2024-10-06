package homeTry.auth.exception.internalServerException;

import homeTry.auth.exception.AuthErrorType;
import homeTry.exception.InternalServerException;

public class KakaoAuthServerException extends InternalServerException {
    public KakaoAuthServerException() {
        super(AuthErrorType.KAKAO_AUTH_SERVER_EXCEPTION);
    }
}
