package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.Exercise;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}
