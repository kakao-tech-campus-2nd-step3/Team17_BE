package homeTry.team.dto;

import org.springframework.data.domain.Page;

import java.time.Duration;

public record RankingResponse(
        String myRanking,
        String myNickname,
        Duration myExerciseTime,
        Page<RankingDTO> page
) {

}
