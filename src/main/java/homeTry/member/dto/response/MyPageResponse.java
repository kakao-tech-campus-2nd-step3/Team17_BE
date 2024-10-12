package homeTry.member.dto.response;

import java.time.Duration;

public record MyPageResponse(
        String nickname,
        String email,
        Duration weeklyTotal,
        Duration monthlyTotal
) {

}