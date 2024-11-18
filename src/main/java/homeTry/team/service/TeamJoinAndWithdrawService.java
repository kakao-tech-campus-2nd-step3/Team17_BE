package homeTry.team.service;

import homeTry.chatting.service.ChattingService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.service.MemberService;
import homeTry.team.exception.badRequestException.TeamNotFoundException;
import homeTry.team.model.entity.Team;
import homeTry.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamJoinAndWithdrawService {
    private final TeamService teamService;
    private final ChattingService chattingService;
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final TeamMemberMappingService teamMemberMappingService;
    private final TeamTagMappingService teamTagMappingService;

    public TeamJoinAndWithdrawService(
            TeamService teamService,
            ChattingService chattingService,
            TeamRepository teamRepository,
            MemberService memberService,
            TeamMemberMappingService teamMemberMappingService,
            TeamTagMappingService teamTagMappingService) {
        this.teamService = teamService;
        this.chattingService = chattingService;
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.teamMemberMappingService = teamMemberMappingService;
        this.teamTagMappingService = teamTagMappingService;
    }


    public synchronized void joinTeam(MemberDTO memberDTO, Long teamId) {
        teamService.joinTeam(memberDTO, teamId);
    }

    //팀 삭제 기능
    @Transactional
    public void deleteTeam(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        chattingService.deleteChattingMessageAll(teamId);

        teamService.deleteTeam(memberId, team);
    }

    public synchronized void withdrawTeam(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        teamService.withdrawTeam(memberId, team);
    }
}