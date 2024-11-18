package homeTry.exerciseList.repository;

import homeTry.exerciseList.model.entity.ExerciseTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseTimeRepository extends JpaRepository<ExerciseTime, Long> {

    // ExerciseTime 리스트 조회 - 멤버 ID와 날짜(당일), 유효한(삭제되지 않은) 운동만
    @Query("SELECT et FROM ExerciseTime et " +
        "JOIN FETCH et.exercise e " +
        "WHERE e.member.id = :memberId " +
        "AND et.startTime BETWEEN :startTime AND :endTime " +
        "AND e.isDeprecated = false")
    List<ExerciseTime> findValidExerciseTimesForMemberOnDate(
        @Param("memberId") Long memberId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime);

    Optional<ExerciseTime> findByExerciseId(Long exerciseId);

    @Query("SELECT COUNT(et) FROM ExerciseTime et " +
            "JOIN et.exercise e " +
            "WHERE e.member.id = :memberId AND et.isActive = true")
    long countActiveExercisesByMemberId(@Param("memberId") Long memberId);

    void deleteByExerciseId(Long exerciseId);
}
