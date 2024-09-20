package homeTry.diary.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

import homeTry.diary.model.vo.ExerciseRecord;
import homeTry.diary.model.vo.Memo;
@Entity
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Embedded
    @Column(nullable = false)
    private Memo memo;

    @Embedded
    private ExerciseRecord exerciseRecord;

    @Column(nullable = false)
    private String memberEmail;

    public Diary(LocalDateTime createAt, ExerciseRecord exerciseRecord, Memo memo, String memberEmail) {
        this.createAt = createAt;
        this.exerciseRecord = exerciseRecord;
        this.memo = memo;
        this.memberEmail = memberEmail;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public ExerciseRecord getExerciseRecord() {
        return exerciseRecord;
    }

    public Memo getMemo() {
        return memo;
    }

    public String getMemberEmail() {
        return memberEmail;
    }
}
