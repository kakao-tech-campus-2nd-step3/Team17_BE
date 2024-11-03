package homeTry.team.dto.response;

import homeTry.team.dto.RankingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.time.Duration;

public record RankingResponse(
        int myRanking,
        String myNickname,
        Long myExerciseTime,
        Slice<RankingDTO> slice
) {

}
