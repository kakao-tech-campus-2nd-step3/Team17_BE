package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.Exercise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findByIdAndMemberId(Long exerciseId, Long memberId);

    List<Exercise> findAllByMemberId(Long memberId);

}
