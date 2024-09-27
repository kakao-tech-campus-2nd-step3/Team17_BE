package homeTry.exerciseList.model.entity;

import homeTry.exerciseList.model.vo.ExerciseName;
import homeTry.member.model.entity.Member;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.Duration;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_time_id")
    private ExerciseTime currentExerciseTime;

    protected Exercise() {
    }

    public Exercise(String exerciseName, Member member) {
        this.exerciseName = new ExerciseName(exerciseName);
        this.isDeprecated = false;
        this.member = member;
        this.currentExerciseTime = new ExerciseTime(LocalDateTime.now());
    }

    public void startExercise() {
        this.currentExerciseTime.setStartTime(LocalDateTime.now());
    }

    public void stopExercise() {
        if(currentExerciseTime != null && currentExerciseTime.getStartTime() != null) {
            currentExerciseTime.endExercise(LocalDateTime.now());
        }
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

    public ExerciseTime getCurrentExerciseTime() {
        return currentExerciseTime;
    }

    public void markAsDeprecated() {
        this.isDeprecated = true;
    }

    public Duration calculateDuration() {
        return currentExerciseTime.getExerciseTime();
    }

}
