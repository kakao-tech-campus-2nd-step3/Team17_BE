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

    @Column(name = "is_deprecated", nullable = false)
    private boolean isDeprecated;

    @ManyToOne
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    private Member member;

    protected Exercise() {
    }

    public Exercise(String exerciseName, Member member) {
        this.exerciseName = new ExerciseName(exerciseName);
        this.isDeprecated = false;
        this.member = member;
    }

    public Long getExerciseId() {
        return this.id;
    }

    public String getExerciseName() {
        return this.exerciseName.value();
    }

    public boolean isDeprecated() {
        return this.isDeprecated;
    }

    public Member getMember() {
        return this.member;
    }

    public void markAsDeprecated() {
        this.isDeprecated = true;
    }

    public void startExercise() {

    }

    public void stopExercise() {

    }
}
