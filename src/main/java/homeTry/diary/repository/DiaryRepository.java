package homeTry.diary.repository;

import homeTry.diary.model.entity.Diary;
import homeTry.member.model.vo.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

@Query("SELECT d FROM Diary d WHERE DATE(d.createAt) = :date AND d.memberEmail = :memberEmail")
    Optional<Diary> findByDateAndMemberEmail(@Param("date") LocalDate date, @Param("memberEmail") Email memberEmail);;


}

