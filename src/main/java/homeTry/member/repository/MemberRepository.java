package homeTry.member.repository;

import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Long countByEmail(Email email);

    Optional<Member> findByEmail(Email email);
}
