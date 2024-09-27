package homeTry.auth.kakaoAuth.service;

import homeTry.member.dto.MemberDTO;
import homeTry.exception.clientException.BadRequestException;
import homeTry.exception.clientException.UserNotFoundException;
import homeTry.exception.serverException.InternalServerException;
import homeTry.member.service.MemberService;
import org.springframework.stereotype.Service;


@Service
public class KakaoAuthService {
    private final KakaoTokenService kakaoTokenService;
    private final MemberService memberService;

    public KakaoAuthService(KakaoTokenService kakaoTokenService, MemberService memberService) {
        this.kakaoTokenService = kakaoTokenService;
        this.memberService = memberService;
    }

    public MemberDTO loginOrRegister(String code) {
        String accessToken = kakaoTokenService.getAccessToken(code);
        MemberDTO memberDTO = kakaoTokenService.getMemberInfo(accessToken);

        try{
            Long id = memberService.login(memberDTO); // -> UserNotFoundException을 던질 수 있음
            memberService.setMemeberAccessToken(id, accessToken);

            return memberDTO;
        } catch (UserNotFoundException e) { //유저를 못 찾으면 회원가입
            Long id = memberService.register(memberDTO);
            memberService.setMemeberAccessToken(id, accessToken);

            return memberDTO;
        } catch (InternalServerException | BadRequestException e){
            throw e;
        } catch (Exception e){
            throw new InternalServerException(e.getMessage());
        }
    }
}
