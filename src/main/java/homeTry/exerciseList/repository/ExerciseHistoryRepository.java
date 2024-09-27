package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.member.model.vo.Email;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseHistoryRepository extends JpaRepository<ExerciseHistory, Long> {

    List<ExerciseHistory> findByExerciseMemberEmailAndCreatedAtBetween(Email memberEmail, LocalDateTime startDate, LocalDateTime endDate);

}