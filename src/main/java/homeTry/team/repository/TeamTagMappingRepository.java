package homeTry.team.repository;

import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagMappingRepository extends JpaRepository<TeamTagMapping, Long> {

    List<TeamTagMapping> findByTeam(Team team);

    void deleteByTeam(Team team);
}
