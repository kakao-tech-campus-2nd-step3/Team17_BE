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

    protected ExerciseTime() {
        this.exerciseTime = Duration.ZERO;
    }

    public ExerciseTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.exerciseTime = Duration.ZERO;
    }

    public void endExercise(LocalDateTime endTime) {
        Duration timeElapsed = Duration.between(this.startTime, endTime);
        this.exerciseTime = this.exerciseTime.plus(timeElapsed);
    }

    public Duration getExerciseTime() {
        return exerciseTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void resetExerciseTime(Duration exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

}
