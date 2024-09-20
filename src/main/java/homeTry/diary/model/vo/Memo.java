package homeTry.diary.model.vo;


import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Memo {
    
    private final String value;

    public Memo(String value) {
        //constraints
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memo memo = (Memo) o;
        return Objects.equals(value, memo.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "Memo{" +
                "value='" + value + '\'' +
                '}';
    }
}

