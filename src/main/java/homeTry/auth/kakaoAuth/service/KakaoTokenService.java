package homeTry.auth.kakaoAuth.service;

import homeTry.auth.kakaoAuth.config.KakaoAuthConfig;
import homeTry.exception.clientException.BadRequestException;
import homeTry.exception.serverException.InternalServerException;
import homeTry.auth.kakaoAuth.dto.KakaoMemberInfoDTO;
import homeTry.member.dto.MemberDTO;
import homeTry.auth.kakaoAuth.dto.TokenResponseDTO;
import java.net.URI;
import java.util.Objects;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoTokenService {

    private final KakaoAuthConfig kakaoAuthConfig;

    private final RestClient client = RestClient.builder().build();

    public KakaoTokenService(KakaoAuthConfig kakaoAuthConfig) {
        this.kakaoAuthConfig = kakaoAuthConfig;
    }

    public String getAccessToken(String code){
        try {
            var body = makeBody(kakaoAuthConfig.getRestApiKey(), kakaoAuthConfig.getRedirectUri(), code);
            ResponseEntity<TokenResponseDTO> result = client.post()
                    .uri(URI.create(kakaoAuthConfig.getTokenUrl())).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body).retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new BadRequestException("잘못된 요청으로 인한 오류입니다.\n" + response.getBody()
                                .toString().replace("{", "").replace("}", "").trim()
                        );
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new InternalServerException("서버에서 오류가 발생하였습니다.\n" + response.getBody()
                                .toString().replace("{", "").replace("}", "").trim()
                        );
                    }).toEntity(TokenResponseDTO.class);

            return Objects.requireNonNull(result.getBody()).accessToken();
        } catch (BadRequestException | InternalServerException e){
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    public MemberDTO getMemberInfo(String kakaoAccessToken){
        try {
            ResponseEntity<KakaoMemberInfoDTO> responseUserInfo = client.get()
                    .uri(URI.create(kakaoAuthConfig.getUserInfoUrl()))
                    .header("Authorization", "Bearer " + kakaoAccessToken)
                    .header("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new BadRequestException("잘못된 요청으로 인한 오류입니다.\n" + response.getBody()
                                .toString().replace("{", "").replace("}", "").trim()
                        );
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new InternalServerException("서버에서 오류가 발생하였습니다.\n" + response.getBody()
                                .toString().replace("{", "").replace("}", "").trim()
                        );
                    }).toEntity(KakaoMemberInfoDTO.class);

            KakaoMemberInfoDTO userInfo = responseUserInfo.getBody();
            KakaoMemberInfoDTO.KakaoAccount kakaoAccount = Objects.requireNonNull(userInfo).kakaoAccount();
            KakaoMemberInfoDTO.KakaoAccount.Profile profile = kakaoAccount.profile();

            return new MemberDTO(kakaoAccount.email(), "default",  profile.nickname());
        } catch (BadRequestException | InternalServerException e){
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }


    private LinkedMultiValueMap<String, String> makeBody(String clientId, String kakaoRedirectUri, String code){
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_url", kakaoRedirectUri);
        body.add("code", code);
        return body;
    }

}

