package homeTry.diary.repository;

import homeTry.diary.model.entity.Diary;
import homeTry.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByCreatedAtBetweenAndMember(LocalDateTime startOfDay, LocalDateTime endOfDay, Member member);

}

