package homeTry.diary.repository;

import homeTry.diary.model.entity.Diary;
import homeTry.member.model.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Slice<Diary> findByCreatedAtBetweenAndMemberOrderByCreatedAtDesc(
            LocalDateTime startOfDay, LocalDateTime endOfDay, Member member, Pageable pageable);

}

