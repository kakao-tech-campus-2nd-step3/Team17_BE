package homeTry.exerciseList.model.entity;

import homeTry.common.entity.SoftDeletableEntity;
import homeTry.exerciseList.model.vo.ExerciseName;
import homeTry.member.model.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(
    name = "exercise",
    indexes = {
        @Index(name = "idx_exercise_member_id", columnList = "member_id"),
        @Index(name = "idx_exercise_is_deprecated", columnList = "is_deprecated")
    }
)
public class Exercise extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "exercise_name", nullable = false))
    private ExerciseName exerciseName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    protected Exercise() {
    }

    public Exercise(String exerciseName, Member member) {
        this.exerciseName = new ExerciseName(exerciseName);
        this.member = member;
    }

    public Long getExerciseId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName.value();
    }

    public Member getMember() {
        return member;
    }

}
