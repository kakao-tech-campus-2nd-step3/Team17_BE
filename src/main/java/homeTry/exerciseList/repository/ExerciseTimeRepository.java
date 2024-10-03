package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseTime;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTimeRepository extends JpaRepository<ExerciseTime, Long> {

    @Query("SELECT et FROM Exercise e JOIN e.currentExerciseTime et WHERE e.member.id = :memberId AND et.startTime BETWEEN :startTime AND :endTime")
    List<ExerciseTime> findByMemberIdAndStartTimeBetween(Long memberId, LocalDateTime startTime, LocalDateTime endTime);

}
