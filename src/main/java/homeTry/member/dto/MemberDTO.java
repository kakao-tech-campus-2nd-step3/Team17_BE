package homeTry.member.dto;

import homeTry.member.model.entity.Member;

public record MemberDTO(
    Long id,
    String email,
    String nickname
) {

    public Member convertToMember() {
        return new Member(this.email, this.nickname);
    }

    public static MemberDTO convertToMemberDTO(Member member) {
        return new MemberDTO(member.getId(), member.getEmail(), member.getNickname());
    }

}
