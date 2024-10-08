package homeTry.common.auth.kakaoAuth.service;

import homeTry.member.dto.MemberDTO;
import homeTry.member.exception.badRequestException.LoginFailedException;
import homeTry.member.service.MemberService;
import org.springframework.stereotype.Service;


@Service
public class KakaoAuthService {

    private final KakaoClientService kakaoClientService;
    private final MemberService memberService;

    public KakaoAuthService(KakaoClientService kakaoClientService, MemberService memberService) {
        this.kakaoClientService = kakaoClientService;
        this.memberService = memberService;
    }

    public MemberDTO loginOrRegister(String code) {
        String accessToken = kakaoClientService.getAccessToken(code);
        MemberDTO memberDTO = kakaoClientService.getMemberInfo(accessToken);

        try {
            Long id = memberService.login(memberDTO); // -> LoginFailedException을 던질 수 있음
            MemberDTO memberDTOWithId = new MemberDTO(id, memberDTO.email(), memberDTO.nickname());

            memberService.setMemeberAccessToken(id, accessToken);
            return memberDTOWithId;
        } catch (LoginFailedException e) { //유저를 못 찾으면 회원가입
            Long id = memberService.register(memberDTO);
            MemberDTO memberDTOWithId = new MemberDTO(id, memberDTO.email(), memberDTO.nickname());

            memberService.setMemeberAccessToken(id, accessToken);
            return memberDTOWithId;
        }
    }
}
