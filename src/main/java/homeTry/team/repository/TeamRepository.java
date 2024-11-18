package homeTry.team.repository;

import homeTry.member.model.entity.Member;
import homeTry.tag.teamTag.model.entity.TeamTag;
import homeTry.team.model.entity.Team;
import homeTry.team.model.vo.Name;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(Name name);

    @Query("SELECT DISTINCT t " +
            "FROM Team t " +
            "WHERE t.id NOT IN (SELECT tm.team.id " +
            "                   FROM TeamMemberMapping tm " +
            "                   WHERE tm.member = :member" +
            "                   AND tm.isDeprecated = false) " +
            "AND t IN (SELECT tt.team " +
            "          FROM TeamTagMapping tt " +
            "          WHERE tt.teamTag IN :tagList " +
            "          GROUP BY tt.team " +
            "          HAVING COUNT(DISTINCT tt.teamTag) = :tagListSize)"
    )
    Slice<Team> findTaggedTeamExcludingMember(@Param("tagList") List<TeamTag> tagList, @Param("tagListSize") long tagListSize, @Param("member") Member member, Pageable pageable);

    @Query("SELECT DISTINCT t " +
            "FROM Team t " +
            "WHERE t.id NOT IN (SELECT tm.team.id " +
            "                   FROM TeamMemberMapping tm " +
            "                   WHERE tm.member = :member" +
            "                   AND tm.isDeprecated = false) " +
            "AND t IN (SELECT tt.team " +
            "          FROM TeamTagMapping tt " +
            "          WHERE tt.teamTag IN :tagList " +
            "          GROUP BY tt.team " +
            "          HAVING COUNT(DISTINCT tt.teamTag) = :tagListSize)" +
            "AND t.teamName.value LIKE CONCAT ('%', :teamName, '%') "
    )
    Slice<Team> findByTeamNameAndTagListExcludingMember(@Param("tagList") List<TeamTag> tagList, @Param("tagListSize") long tagListSize, @Param("member") Member member, Pageable pageable, @Param("teamName") String teamName);

    @Query("SELECT DISTINCT t " +
            "FROM Team t " +
            "WHERE t.id NOT IN (SELECT tm.team.id " +
            "                   FROM TeamMemberMapping tm " +
            "                   WHERE tm.member = :member" +
            "                   AND tm.isDeprecated = false) " +
            "AND t.teamName.value LIKE CONCAT ('%', :teamName, '%') "
    )
    Slice<Team> findByTeamNameExcludingMember(@Param("teamName") String teamName, @Param("member") Member member, Pageable pageable);

    @Query("SELECT DISTINCT t " +
            "FROM Team t " +
            "WHERE t.id NOT IN (SELECT tm.team.id " +
            "                   FROM TeamMemberMapping tm " +
            "                   WHERE tm.member = :member" +
            "                   AND tm.isDeprecated = false) "
    )
    Slice<Team> findTeamExcludingMember(@Param("member") Member member, Pageable pageable);
}
