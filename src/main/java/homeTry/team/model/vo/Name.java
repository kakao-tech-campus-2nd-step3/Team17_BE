package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {

    private String value;

    protected Name() {
    }

    public Name(String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(String value) {
        if(value == null || value.isBlank())
            throw new IllegalArgumentException("이름값은 필수입니다");

    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Name))
            return false;

        Name name = (Name) object;
        return Objects.equals(this.value, name.getValue());
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
