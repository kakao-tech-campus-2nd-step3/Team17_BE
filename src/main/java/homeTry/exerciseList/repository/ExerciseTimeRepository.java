package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTimeRepository extends JpaRepository<ExerciseTime, Long> {

    List<ExerciseTime> findByExerciseMemberIdAndStartTimeBetween(
        Long memberId, LocalDateTime startTime, LocalDateTime endTime);

    Optional<ExerciseTime> findByExerciseId(Long exerciseId);

}
