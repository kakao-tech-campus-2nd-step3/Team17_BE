package homeTry.diary.repository;

import homeTry.diary.model.entity.Diary;
import homeTry.member.model.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE d.createdAt BETWEEN :startOfDay AND :endOfDay AND d.member = :member")
    List<Diary> findByDateRangeAndMember(@Param("startOfDay") LocalDateTime startOfDay,
                                             @Param("endOfDay") LocalDateTime endOfDay,
                                             @Param("member") Member member);
}

