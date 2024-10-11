package homeTry.member.dto;

import homeTry.member.model.entity.Member;

public record MemberDTO(
    Long id,
    String email,
    String nickname
) {

    public Member toEntity() {
        return new Member(this.email, this.nickname);
    }

    public static MemberDTO from(Member member) {
        return new MemberDTO(member.getId(), member.getEmail(), member.getNickname());
    }

}
