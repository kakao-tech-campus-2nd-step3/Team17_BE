package homeTry.exerciseList.model.entity;

import homeTry.common.converter.DurationToLongConverter;
import homeTry.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(
    name = "exercise_history",
    indexes = {
        @Index(name = "idx_exercise_id", columnList = "exercise_id"),
        @Index(name = "idx_created_at", columnList = "created_at")
    }
)
public class ExerciseHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    @Convert(converter = DurationToLongConverter.class)
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
