package homeTry.exerciseList.model.entity;

import homeTry.exerciseList.model.vo.ExerciseName;
import homeTry.member.model.entity.Member;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "exercise_name", nullable = false))
    private ExerciseName exerciseName;

    @Column(nullable = false)
    private boolean isDeprecated;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    protected Exercise() {
    }

    public Exercise(String exerciseName, Member member) {
        this.exerciseName = new ExerciseName(exerciseName);
        this.isDeprecated = false;
        this.member = member;
    }

    public Long getExerciseId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName.value();
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public Member getMember() {
        return member;
    }

    public void markAsDeprecated() {
        this.isDeprecated = true;
    }

}
