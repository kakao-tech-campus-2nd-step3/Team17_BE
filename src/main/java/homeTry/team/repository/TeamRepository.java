package homeTry.team.repository;

import homeTry.member.model.entity.Member;
import homeTry.tag.model.entity.Tag;
import homeTry.team.model.entity.Team;
import homeTry.team.model.vo.Name;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(Name name);

    @Query("SELECT DISTINCT t " +
            "FROM Team t " +
            "WHERE t NOT IN (SELECT tm.team " +
            "                FROM TeamMember tm " +
            "                WHERE tm.member = :member)"
    )
    Page<Team> findTeamExcludingMember(@Param("member") Member member, Pageable pageable);

    @Query("SELECT DISTINCT t " +
            "FROM Team t " +
            "WHERE t.id NOT IN (SELECT tm.team.id " +
            "                   FROM TeamMember tm " +
            "                   WHERE tm.member = :member) " +
            "AND t IN (SELECT tt.team " +
            "          FROM TeamTag tt " +
            "          WHERE tt.tag IN :tagList " +
            "          GROUP BY tt.team " +
            "          HAVING COUNT(DISTINCT tt.tag) = :tagListSize)"
    )
    Page<Team> findTaggedTeamExcludingMember(@Param("tagList") List<Tag> tagList, @Param("tagListSize") long tagListSize, @Param("member") Member member, Pageable pageable);
}
