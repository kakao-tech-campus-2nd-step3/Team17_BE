package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTimeRepository extends JpaRepository<ExerciseTime, Long> {

}
