package homeTry.common.auth.kakaoAuth.service;

import homeTry.common.auth.exception.badRequestException.InvalidAuthCodeException;
import homeTry.common.auth.exception.internalServerException.HomeTryServerException;
import homeTry.common.auth.exception.internalServerException.KakaoAuthServerException;
import homeTry.common.auth.kakaoAuth.config.KakaoAuthConfig;
import homeTry.common.auth.kakaoAuth.dto.response.KakaoErrorResponse;
import homeTry.common.auth.kakaoAuth.dto.response.KakaoMemberInfoResponse;
import homeTry.member.dto.MemberDTO;
import homeTry.common.auth.kakaoAuth.dto.response.TokenResponse;
import java.net.URI;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Service
public class KakaoTokenService {

    private final KakaoAuthConfig kakaoAuthConfig;
    private static final Logger logger = LoggerFactory.getLogger(KakaoTokenService.class);
    private final RestClient client = RestClient.builder().build();

    public KakaoTokenService(KakaoAuthConfig kakaoAuthConfig) {
        this.kakaoAuthConfig = kakaoAuthConfig;
    }

    public String getAccessToken(String code) {
        try {
            var body = makeBody(kakaoAuthConfig.restApiKey(), kakaoAuthConfig.redirectUri(),
                code);
            ResponseEntity<TokenResponse> result = client.post()
                .uri(URI.create(kakaoAuthConfig.tokenUrl()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .toEntity(TokenResponse.class);

            return Objects.requireNonNull(result.getBody()).accessToken();
        } catch (HttpClientErrorException e) {
            KakaoErrorResponse kakaoErrorResponse = e.getResponseBodyAs(
                KakaoErrorResponse.class);
            if (Objects.requireNonNull(kakaoErrorResponse).KakaoErrorCode().equals("KOE320")) {
                throw new InvalidAuthCodeException();
            }

            logger.error(kakaoErrorResponse.toString());
            throw new HomeTryServerException();
        } catch (HttpServerErrorException e) {
            KakaoErrorResponse kakaoErrorResponse = e.getResponseBodyAs(
                KakaoErrorResponse.class);
            logger.error(Objects.requireNonNull(kakaoErrorResponse).toString());

            throw new KakaoAuthServerException();
        }
    }

    public MemberDTO getMemberInfo(String kakaoAccessToken) {
        try {
            ResponseEntity<KakaoMemberInfoResponse> responseUserInfo = client.get()
                .uri(URI.create(kakaoAuthConfig.userInfoUrl()))
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .header("Content-type",
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
                .retrieve().toEntity(KakaoMemberInfoResponse.class);

            KakaoMemberInfoResponse userInfo = responseUserInfo.getBody();
            KakaoMemberInfoResponse.KakaoAccount kakaoAccount = Objects.requireNonNull(userInfo)
                .kakaoAccount();
            KakaoMemberInfoResponse.KakaoAccount.Profile profile = kakaoAccount.profile();

            return new MemberDTO(0L, kakaoAccount.email(), profile.nickname());
        } catch (HttpClientErrorException e) {
            KakaoErrorResponse kakaoErrorResponse = e.getResponseBodyAs(
                KakaoErrorResponse.class);

            if (Objects.requireNonNull(kakaoErrorResponse).KakaoErrorCode().equals("KOE320")) {
                throw new InvalidAuthCodeException();
            }

            logger.error(kakaoErrorResponse.toString());
            throw new HomeTryServerException();
        } catch (HttpServerErrorException e) {
            KakaoErrorResponse kakaoErrorResponse = e.getResponseBodyAs(
                KakaoErrorResponse.class);
            logger.error(Objects.requireNonNull(kakaoErrorResponse).toString());

            throw new KakaoAuthServerException();
        }
    }


    private LinkedMultiValueMap<String, String> makeBody(String clientId, String kakaoRedirectUri,
        String code) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_url", kakaoRedirectUri);
        body.add("code", code);
        return body;
    }

}

