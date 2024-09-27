package homeTry.diary.repository;

import homeTry.diary.model.entity.Diary;
import homeTry.member.model.vo.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Optional<Diary> findByDateRangeAndMemberEmail(LocalDateTime start, LocalDateTime end, Email memberEmail);

}

