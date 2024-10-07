package homeTry.auth.kakaoAuth.service;

import homeTry.auth.exception.badRequestException.InvalidAuthCodeException;
import homeTry.auth.exception.internalServerException.HomeTryServerException;
import homeTry.auth.exception.internalServerException.KakaoAuthServerException;
import homeTry.auth.kakaoAuth.config.KakaoAuthConfig;
import homeTry.auth.kakaoAuth.dto.KakaoErrorResponseDTO;
import homeTry.auth.kakaoAuth.dto.KakaoMemberInfoDTO;
import homeTry.member.dto.MemberDTO;
import homeTry.auth.kakaoAuth.dto.TokenResponseDTO;
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
            var body = makeBody(kakaoAuthConfig.getRestApiKey(), kakaoAuthConfig.getRedirectUri(),
                code);
            ResponseEntity<TokenResponseDTO> result = client.post()
                .uri(URI.create(kakaoAuthConfig.getTokenUrl()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .toEntity(TokenResponseDTO.class);

            return Objects.requireNonNull(result.getBody()).accessToken();
        } catch (HttpClientErrorException e) {
            KakaoErrorResponseDTO kakaoErrorResponseDTO = e.getResponseBodyAs(
                KakaoErrorResponseDTO.class);
            if (Objects.requireNonNull(kakaoErrorResponseDTO).KakaoErrorCode().equals("KOE320")) {
                throw new InvalidAuthCodeException();
            }

            logger.error(kakaoErrorResponseDTO.toString());
            throw new HomeTryServerException();
        } catch (HttpServerErrorException e) {
            KakaoErrorResponseDTO kakaoErrorResponseDTO = e.getResponseBodyAs(
                KakaoErrorResponseDTO.class);
            logger.error(Objects.requireNonNull(kakaoErrorResponseDTO).toString());

            throw new KakaoAuthServerException();
        }
    }

    public MemberDTO getMemberInfo(String kakaoAccessToken) {
        try {
            ResponseEntity<KakaoMemberInfoDTO> responseUserInfo = client.get()
                .uri(URI.create(kakaoAuthConfig.getUserInfoUrl()))
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .header("Content-type",
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
                .retrieve().toEntity(KakaoMemberInfoDTO.class);

            KakaoMemberInfoDTO userInfo = responseUserInfo.getBody();
            KakaoMemberInfoDTO.KakaoAccount kakaoAccount = Objects.requireNonNull(userInfo)
                .kakaoAccount();
            KakaoMemberInfoDTO.KakaoAccount.Profile profile = kakaoAccount.profile();

            return new MemberDTO(0L, kakaoAccount.email(), profile.nickname());
        } catch (HttpClientErrorException e) {
            KakaoErrorResponseDTO kakaoErrorResponseDTO = e.getResponseBodyAs(
                KakaoErrorResponseDTO.class);

            if (Objects.requireNonNull(kakaoErrorResponseDTO).KakaoErrorCode().equals("KOE320")) {
                throw new InvalidAuthCodeException();
            }

            logger.error(kakaoErrorResponseDTO.toString());
            throw new HomeTryServerException();
        } catch (HttpServerErrorException e) {
            KakaoErrorResponseDTO kakaoErrorResponseDTO = e.getResponseBodyAs(
                KakaoErrorResponseDTO.class);
            logger.error(Objects.requireNonNull(kakaoErrorResponseDTO).toString());

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

