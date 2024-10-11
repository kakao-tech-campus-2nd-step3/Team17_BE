package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT COUNT(et) FROM ExerciseTime et " +
        "JOIN et.exercise e " +
        "WHERE e.member.id = :memberId AND et.isActive = true")
    long countActiveExercisesByMemberId(@Param("memberId") Long memberId);

}
