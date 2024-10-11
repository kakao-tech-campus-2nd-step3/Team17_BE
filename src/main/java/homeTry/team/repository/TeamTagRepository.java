package homeTry.team.repository;

import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {

    List<TeamTag> findByTeam(Team team);

    void deleteByTeam(Team team);
}
