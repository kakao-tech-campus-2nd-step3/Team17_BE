package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseList;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseListRepository extends JpaRepository<ExerciseList, Long> {

    List<ExerciseList> findExercisesWithinPeriod(LocalDateTime startDate, LocalDateTime endDate);

}
