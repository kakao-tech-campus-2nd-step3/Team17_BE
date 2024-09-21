package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseListRepository extends JpaRepository<ExerciseList, Long> {

}
