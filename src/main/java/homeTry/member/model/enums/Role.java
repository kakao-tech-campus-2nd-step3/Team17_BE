package homeTry.member.model.enums;

public enum Role {
    ADMIN(0),
    USER(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
