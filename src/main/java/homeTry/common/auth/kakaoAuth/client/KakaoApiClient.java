package homeTry.common.auth.kakaoAuth.client;

import homeTry.common.auth.kakaoAuth.config.KakaoAuthConfig;
import homeTry.common.auth.kakaoAuth.dto.KakaoMemberWithdrawDTO;
import homeTry.common.auth.kakaoAuth.dto.response.KakaoMemberInfoResponse;
import homeTry.common.auth.kakaoAuth.dto.response.TokenResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class KakaoApiClient {

    private final KakaoAuthConfig kakaoAuthConfig;
    private final RestClient client;

    public KakaoApiClient(KakaoAuthConfig kakaoAuthConfig) {
        this.kakaoAuthConfig = kakaoAuthConfig;
        this.client = RestClient.builder().build();
    }

    public TokenResponse getToken(String code) {
        var body = makeBody(kakaoAuthConfig.restApiKey(), kakaoAuthConfig.redirectUri(), code);
        return client.post()
                .uri(URI.create(kakaoAuthConfig.tokenUrl()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .toEntity(TokenResponse.class)
                .getBody();
    }

    public KakaoMemberInfoResponse getMemberInfo(String kakaoAccessToken) {
        return client.get()
                .uri(URI.create(kakaoAuthConfig.userInfoUrl()))
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .header("Content-type",
                        MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
                .retrieve()
                .toEntity(KakaoMemberInfoResponse.class)
                .getBody();
    }

    public void unlinkKakao(KakaoMemberWithdrawDTO kakaoMemberWithdrawDTO) {
        client.post()
                .uri(URI.create(kakaoAuthConfig.unlinkUrl()))
                .header("Authorization", "Bearer " + kakaoMemberWithdrawDTO.kakaoAccessToken())
                .header("Content-type",
                        MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset_UTF-8")
                .retrieve();
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