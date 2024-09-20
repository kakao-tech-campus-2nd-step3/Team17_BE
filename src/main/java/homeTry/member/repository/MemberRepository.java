package homeTry.member.repository;

import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Password;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Long countByEmailAndPassword(Email email, Password password);
    Long countByEmail(Email email);

    Optional<Member> findByEmail(Email email);
}
