package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Participant {
    private long value;

    protected Participant() {
    }

    public Participant(long value) {
        validateParticipant(value);
        this.value = value;
    }

    private void validateParticipant(long value) {
        if(value < 0)
            throw new IllegalArgumentException("participant 수는 0 이하일 수 없습니다.");
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Participant))
            return false;

        Participant participant = (Participant) object;
        return Objects.equals(this.value, participant.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
