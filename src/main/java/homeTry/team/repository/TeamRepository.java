package homeTry.team.repository;

import homeTry.team.model.entity.Team;
import homeTry.team.model.vo.Name;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(Name name);
}
