package homeTry.member.dto;

import homeTry.member.model.entity.Member;
import java.time.LocalDateTime;

public record MemberDTO(
        Long id,
        String email,
        String password,
        String nickname
) {

    public Member convertToMember(LocalDateTime registrationDate){ //서비스 단에서 가입일을 정의
        return new Member(this.email, this.password, this.nickname, registrationDate);
    }

    public static MemberDTO convertToMemberDTO(Member member){
        return new MemberDTO(member.getId(), member.getEmail(), member.getPassword(), member.getNickname());
    }

}
