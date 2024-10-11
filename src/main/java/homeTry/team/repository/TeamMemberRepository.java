package homeTry.team.repository;

import homeTry.member.model.entity.Member;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findByTeamAndMember(Team team, Member member);

    void deleteByTeam(Team team); //특정 팀에 속한 모든 엔티티 삭제

    List<TeamMember> findByTeam(Team team);
}
