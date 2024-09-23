package homeTry.exerciseList.model.entity;

import homeTry.exerciseList.model.vo.ExerciseName;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class ExerciseList {

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

    @Column(name = "deprecated", nullable = false)
    private boolean deprecated;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    protected ExerciseList() {
    }

    public ExerciseList(ExerciseName exerciseName, boolean deprecated, boolean isActive) {
        this.exerciseName = exerciseName;
        this.deprecated = deprecated;
        this.isActive = isActive;
        this.exerciseTime = Duration.ZERO;
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
        return deprecated;
    }

    public boolean isActive() {
        return isActive;
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
