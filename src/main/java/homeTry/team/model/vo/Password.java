package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Password {

    private String value;

    protected Password () {}

    public Password(String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(String value) {
        if(value == null || value.isBlank())
            throw new IllegalArgumentException("password 값은 필수입니다");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Password))
            return false;

        Password password = (Password) object;
        return Objects.equals(this.value, password.getValue());
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
