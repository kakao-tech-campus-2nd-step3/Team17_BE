package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseList;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseListRepository extends JpaRepository<ExerciseList, Long> {

    List<ExerciseList> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}
