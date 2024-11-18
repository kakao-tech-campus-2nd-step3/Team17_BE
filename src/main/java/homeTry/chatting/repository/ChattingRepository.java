package homeTry.chatting.repository;

import homeTry.chatting.model.entity.Chatting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRepository extends JpaRepository<Chatting, Long> {
    Slice<Chatting> findByTeamMemberMappingTeamId(Long teamId, Pageable pageable);

    void deleteAllByTeamMemberMappingTeamId(Long teamId);
}
