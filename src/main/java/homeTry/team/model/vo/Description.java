package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Description {

    private String value;

    protected Description () {}

    public Description(String value) {
        validateDescription(value);
        this.value = value;
    }

    private void validateDescription(String value) {
        if (value != null && value.isBlank())
            throw new IllegalArgumentException("description은 빈 공백이면 안됩니다.");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Description))
            return false;

        Description description = (Description) object;
        return Objects.equals(this.value, description.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
