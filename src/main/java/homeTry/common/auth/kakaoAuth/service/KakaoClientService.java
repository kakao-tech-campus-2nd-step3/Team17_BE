package homeTry.common.auth.kakaoAuth.service;

import homeTry.common.auth.exception.badRequestException.InvalidAuthCodeException;
import homeTry.common.auth.exception.internalServerException.HomeTryServerException;
import homeTry.common.auth.exception.internalServerException.KakaoAuthServerException;
import homeTry.common.auth.kakaoAuth.client.KakaoApiClient;
import homeTry.common.auth.kakaoAuth.dto.response.KakaoErrorResponse;
import homeTry.member.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Objects;

@Service
public class KakaoClientService {

    private static final Logger logger = LoggerFactory.getLogger(KakaoClientService.class);
    private final KakaoApiClient kakaoApiClient;

    public KakaoClientService(KakaoApiClient kakaoApiClient) {
        this.kakaoApiClient = kakaoApiClient;
    }

    public String getAccessToken(String code) {
        try {
            return kakaoApiClient.getToken(code).accessToken();
        } catch (HttpClientErrorException e) {
            handleClientError(e);
        } catch (HttpServerErrorException e) {
            handleServerError(e);
        }
        throw new HomeTryServerException();
    }

    public MemberDTO getMemberInfo(String kakaoAccessToken) {
        try {
            var userInfo = kakaoApiClient.getMemberInfo(kakaoAccessToken);
            var kakaoAccount = userInfo.kakaoAccount();
            var profile = kakaoAccount.profile();
            return new MemberDTO(0L, kakaoAccount.email(), profile.nickname());
        } catch (HttpClientErrorException e) {
            handleClientError(e);
        } catch (HttpServerErrorException e) {
            handleServerError(e);
        }
        throw new HomeTryServerException();
    }

    private void handleClientError(HttpClientErrorException e) {
        KakaoErrorResponse kakaoErrorResponse = e.getResponseBodyAs(KakaoErrorResponse.class);
        if (Objects.requireNonNull(kakaoErrorResponse).KakaoErrorCode().equals("KOE320")) {
            throw new InvalidAuthCodeException();
        }
        logger.error(kakaoErrorResponse.toString());
        throw new HomeTryServerException();
    }

    private void handleServerError(HttpServerErrorException e) {
        KakaoErrorResponse kakaoErrorResponse = e.getResponseBodyAs(KakaoErrorResponse.class);
        logger.error(Objects.requireNonNull(kakaoErrorResponse).toString());
        throw new KakaoAuthServerException();
    }
}