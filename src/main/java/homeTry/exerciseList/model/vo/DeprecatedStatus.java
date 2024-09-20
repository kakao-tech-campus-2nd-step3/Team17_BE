package homeTry.exerciseList.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class DeprecatedStatus {

    private boolean value;

    protected DeprecatedStatus() {
    }

    public DeprecatedStatus(boolean value) {
        this.value = value;
    }

    public boolean isDeprecated() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        DeprecatedStatus isDeprecated = (DeprecatedStatus) object;
        return value == isDeprecated.value;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

}
