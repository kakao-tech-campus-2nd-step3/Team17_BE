package homeTry.exerciseList.model.entity;

import homeTry.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
public class ExerciseHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Duration exerciseHistoryTime;

    protected ExerciseHistory() {

    }

    public ExerciseHistory(Exercise exercise, Duration exerciseHistoryTime) {
        this.exercise = exercise;
        this.exerciseHistoryTime = exerciseHistoryTime;
    }

    public Long getExerciseHistoryId() {
        return id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Duration getExerciseHistoryTime() {
        return exerciseHistoryTime;
    }

}
