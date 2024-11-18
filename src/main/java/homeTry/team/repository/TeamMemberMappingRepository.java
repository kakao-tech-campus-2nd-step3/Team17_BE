package homeTry.team.repository;

import homeTry.member.model.entity.Member;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMemberMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamMemberMappingRepository extends JpaRepository<TeamMemberMapping, Long> {

    @Query("SELECT tm " +
            "FROM TeamMemberMapping tm " +
            "WHERE tm.member = :member " +
            "AND tm.team = :team " +
            "AND tm.isDeprecated = false"
    )
    Optional<TeamMemberMapping> findByTeamAndMember(@Param("team") Team team, @Param("member") Member member);

    @Query("SELECT tm " +
            "FROM TeamMemberMapping tm " +
            "WHERE tm.member = :member " +
            "AND tm.team = :team "
    )
    Optional<TeamMemberMapping> findByTeamAndMemberAndActivated(@Param("team") Team team, @Param("member") Member member);

    Optional<TeamMemberMapping> findByTeamIdAndMemberId(Long memberId, Long teamId);

    void deleteByTeam(Team team); //특정 팀에 속한 모든 엔티티 삭제

    @Query("SELECT tm " +
            "FROM TeamMemberMapping tm " +
            "WHERE tm.team = :team " +
            "AND tm.isDeprecated = false"
    )
    List<TeamMemberMapping> findByTeam(@Param("team") Team team);

    @Query("SELECT tm " +
            "FROM TeamMemberMapping tm " +
            "WHERE tm.member = :member " +
            "AND tm.isDeprecated = false"
    )
    List<TeamMemberMapping> findByMember(@Param("member") Member member);
}
