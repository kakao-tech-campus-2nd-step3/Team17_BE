package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record Participant (long value){

    public Participant {
        validateParticipant(value);
    }

    private void validateParticipant(long value) {
        if(value < 0)
            throw new IllegalArgumentException("participant 수는 0 이하일 수 없습니다.");
    }
}
