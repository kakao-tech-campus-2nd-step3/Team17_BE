package homeTry.team.dto;

import org.springframework.data.domain.Page;

import java.time.Duration;

public record RankingResponse(
        int myRanking,
        String myNickname,
        Duration myExerciseTime,
        Page<RankingDTO> page
) {

}
