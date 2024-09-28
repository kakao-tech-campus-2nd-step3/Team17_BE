package homeTry.team.repository;

import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeam(Team team);
}
