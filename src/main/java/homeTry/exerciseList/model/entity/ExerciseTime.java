package homeTry.exerciseList.model.entity;

import homeTry.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class ExerciseTime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Duration exerciseTime = Duration.ZERO;

    @Column(nullable = false)
    private boolean isActive = false;

    @OneToOne
    @JoinColumn(nullable = false)
    private Exercise exercise;

    protected ExerciseTime() {
    }

    public ExerciseTime(Exercise exercise) {
        this.exercise = exercise;
        this.startTime = LocalDateTime.now();
        isActive = true;
    }

    public void startExercise() {
        this.startTime = LocalDateTime.now();
        this.isActive = true;
    }

    public void stopExercise() {
        if (isActive) {
            Duration timeElapsed = Duration.between(this.startTime, LocalDateTime.now());
            this.exerciseTime = this.exerciseTime.plus(timeElapsed);
            this.isActive = false;
        }
    }

    public void stopExerciseWithoutSavingTime() {
        if (isActive) {
            this.isActive = false;  // 운동을 종료하지만 시간을 더하지 않음
        }
    }

    public void resetExerciseTime() {
        this.exerciseTime = Duration.ZERO;
        this.isActive = false;
    }

    public Long getId() {
        return id;
    }

    public Duration getExerciseTime() {
        return exerciseTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public Exercise getExercise() {
        return exercise;
    }

}
