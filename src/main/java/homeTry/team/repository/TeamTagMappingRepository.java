package homeTry.team.repository;

import homeTry.tag.teamTag.model.entity.TeamTag;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamTagMappingRepository extends JpaRepository<TeamTagMapping, Long> {

    @Query("SELECT ttm " +
            "FROM TeamTagMapping ttm " +
            "WHERE ttm.team = :team " +
            "AND ttm.isDeprecated = false"
    )
    List<TeamTagMapping> findByTeam(@Param("team") Team team);

    void deleteByTeam(Team team);

    TeamTagMapping findByTeamTag(TeamTag teamTag);
}
