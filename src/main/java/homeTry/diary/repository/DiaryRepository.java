package homeTry.diary.repository;

import homeTry.diary.model.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE d.createAt >= :startDate AND d.createAt < :endDate")
    Optional<Diary> findByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}

