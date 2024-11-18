package homeTry.common.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeletableEntity extends BaseEntity {

    private boolean isDeprecated;

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void markAsDeprecated() {
        this.isDeprecated = true;
    }

    public void markAsActivated() {
        this.isDeprecated = false;
    }

}
