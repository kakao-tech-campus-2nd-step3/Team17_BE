package homeTry.member.dto;

import java.time.Duration;

public record MyPageDTO(
    String nickname,
    String email,
    Duration weeklyTotal,
    Duration monthlyTotal
) {

}