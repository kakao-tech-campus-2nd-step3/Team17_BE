package homeTry.exerciseList.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class ExerciseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "exercise_history_time", nullable = false)
    private Duration exerciseHistoryTime;

    protected ExerciseHistory() {

    }

    public ExerciseHistory(Exercise exercise, Duration exerciseHistoryTime) {
        this.exercise = exercise;
        this.createdAt = LocalDateTime.now();
        this.exerciseHistoryTime = exerciseHistoryTime;
    }

    public Long getExerciseHistoryId() {
        return id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Duration getExerciseHistoryTime() {
        return exerciseHistoryTime;
    }

}
