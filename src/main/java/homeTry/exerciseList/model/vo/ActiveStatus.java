package homeTry.exerciseList.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class ActiveStatus {

    private boolean value;

    protected ActiveStatus() {
    }

    public ActiveStatus(boolean value) {
        this.value = value;
    }

    public boolean isActive() {
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

        ActiveStatus isActive = (ActiveStatus) object;
        return value == isActive.value;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

}
