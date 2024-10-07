package homeTry.team.repository;

import homeTry.tag.model.entity.Tag;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {

    List<TeamTag> findByTeam(Team team);

    void deleteByTeam(Team team);

    @Query("SELECT tt.team " +
        "FROM TeamTag tt " +
        "WHERE tt.tag IN :tagList " +
        "GROUP BY tt.team " +
        "HAVING COUNT(DISTINCT tt.tag) = :tagListSize")
    Page<Team> findTeamsByTags(@Param("tagList") List<Tag> tagList,
        @Param("tagListSize") long tagListSize, Pageable pageable);
}
