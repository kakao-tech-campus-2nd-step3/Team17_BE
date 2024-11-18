package homeTry.member.dto.response;

public record MyPageResponse(
    Long id,
    String nickname,
    String email,
    Integer attendance,
    Long weeklyTotal,
    Long monthlyTotal
) {

}