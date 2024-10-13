package homeTry.team.service;

import homeTry.member.model.entity.Member;
import homeTry.team.exception.AlreadyJoinedTeamException;
import homeTry.team.exception.TeamMemberNotFoundException;
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

    //TeamMember 엔티티 추가 (멤버가 팀 가입시 사용)
    public void addTeamMember(Team team, Member member) {
        teamMemberRepository.findByTeamAndMember(team, member) // 이미 가입되어 있는경우 예외 던짐
                .ifPresent(teamMember -> {
                    throw new AlreadyJoinedTeamException();
                });

        teamMemberRepository.save(new TeamMember(member, team));
    }

    //TeamMember 엔티티 삭제 (멤버가 팀에서 나갈 시)
    public void deleteTeamMember(Team team, Member member) {
        TeamMember teamMember = teamMemberRepository.findByTeamAndMember(team, member)
                .orElseThrow(() -> new TeamMemberNotFoundException());

        teamMemberRepository.delete(teamMember);
    }

    //팀에 속한 멤버들의 TeamMember 를 모두 삭제
    public void deleteAllTeamMemberFromTeam(Team team) { //특정 팀에 대한 모든 TeamMember 삭제 (팀 삭제 시)
        teamMemberRepository.deleteByTeam(team);
    }

    //팀에 속한 멤버들의 TeamMEmber 를 반환
    public List<TeamMember> getTeamMember(Team team) {
        return teamMemberRepository.findByTeam(team);
    }
}
