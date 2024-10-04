package homeTry.team.service;

import homeTry.member.model.entity.Member;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMember;
import homeTry.team.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public void addTeamMember (Team team, Member member){ //TeamMember 엔티티 추가 (멤버가 팀 가입시 사용)
        teamMemberRepository.save(new TeamMember(member, team));
    }

    public void deleteTeamMember(Team team, Member member) { //TeamMember 엔티티 삭제 (멤버가 팀에서 나갈 시)
        TeamMember teamMember = teamMemberRepository.findByTeamAndMember(team, member)
                .orElseThrow(()-> new IllegalArgumentException("해당되는 TeamMember가 없습니다"));

        teamMemberRepository.delete(teamMember);
    }

    public void deleteAllTeamMemberFromTeam(Team team) { //특정 팀에 대한 모든 TeamMember 삭제 (팀 삭제 시)
        teamMemberRepository.deleteByTeam(team);
    }

}
