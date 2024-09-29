package homeTry.exerciseList.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class ExerciseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "exercise_time", nullable = false)
    private Duration exerciseTime;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    protected ExerciseTime() {
        this.exerciseTime = Duration.ZERO;
        this.isActive = false;
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

    public void resetExerciseTime() {
        this.exerciseTime = Duration.ZERO;
        this.isActive = false;
    }

}
