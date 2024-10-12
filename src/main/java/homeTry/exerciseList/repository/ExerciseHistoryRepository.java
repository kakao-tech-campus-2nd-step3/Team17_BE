package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseHistoryRepository extends JpaRepository<ExerciseHistory, Long> {

    List<ExerciseHistory> findByExerciseMemberIdAndCreatedAtBetween(
            Long memberId, LocalDateTime startDate, LocalDateTime endDate);

}
