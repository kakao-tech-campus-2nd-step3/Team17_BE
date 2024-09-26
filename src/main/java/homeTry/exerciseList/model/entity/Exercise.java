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

    @Column(name = "exercise_time", nullable = false)
    private Duration exerciseTime; // 누적 운동 시간

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // 운동 시작 시간

    @Column(name = "is_deprecated", nullable = false)
    private boolean isDeprecated;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    private Member member;

    protected Exercise() {
    }

    public Exercise(String exerciseName, Member member) {
        this.exerciseName = new ExerciseName(exerciseName);
        this.isDeprecated = false;
        this.isActive = false;
        this.exerciseTime = Duration.ZERO;
        this.member = member;
    }

    public Long getExerciseId() {
        return id;
    }

    public ExerciseName getExerciseName() {
        return exerciseName;
    }

    public Duration getExerciseTime() {
        return exerciseTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public boolean isActive() {
        return isActive;
    }

    public Member getMember() {
        return member;
    }

    public void markAsDeprecated() {
        this.isDeprecated = true;
    }

    public void startExercise() {
        if (!this.isActive) {
            this.startTime = LocalDateTime.now();
            this.isActive = true; // 운동 상태를 활성화
        }
    }

    public void stopExercise() {
        if (this.isActive && this.startTime != null) {
            // 경과 시간을 계산하고 누적된 운동 시간에 더함
            Duration timeElapsed = Duration.between(this.startTime, LocalDateTime.now());
            this.exerciseTime = this.exerciseTime.plus(timeElapsed); // 누적된 운동 시간에 경과 시간 추가
            this.isActive = false; // 운동 상태를 비활성화
        }
    }
}