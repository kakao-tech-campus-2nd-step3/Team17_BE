package homeTry.common.auth.exception.internalServerException;

import homeTry.common.auth.exception.AuthErrorType;
import homeTry.common.exception.InternalServerException;

public class KakaoAuthServerException extends InternalServerException {

    public KakaoAuthServerException() {
        super(AuthErrorType.KAKAO_AUTH_SERVER_EXCEPTION);
    }
}
