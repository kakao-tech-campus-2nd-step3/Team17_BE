package homeTry.member.service;

import homeTry.member.model.entity.Member;
import homeTry.team.model.entity.Team;
import homeTry.team.service.TeamMemberMappingService;
import homeTry.team.service.TeamJoinAndWithdrawService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberTeamWithdrawService {

    private final TeamMemberMappingService teamMemberMappingService;
    private final TeamJoinAndWithdrawService teamJoinAndWithdrawService;
    private final MemberService memberService;

    public MemberTeamWithdrawService(TeamMemberMappingService teamMemberMappingService,
                                     TeamJoinAndWithdrawService teamJoinAndWithdrawService, MemberService memberService) {
        this.teamMemberMappingService = teamMemberMappingService;
        this.teamJoinAndWithdrawService = teamJoinAndWithdrawService;
        this.memberService = memberService;
    }

    @Transactional
    public void withdrawTeamByWithdrawMember(Long withdrawMemberId) {
        Member withdrawMember = memberService.getMemberEntity(withdrawMemberId);

        List<Team> withdrawTeamList = teamMemberMappingService.getTeamListByMember(withdrawMember);

        for (Team team : withdrawTeamList) {
            if (team.getLeaderId().equals(withdrawMemberId)) {
                teamJoinAndWithdrawService.deleteTeam(withdrawMemberId, team.getId());
                continue;
            }
            teamJoinAndWithdrawService.withdrawTeam(withdrawMemberId, team.getId());
        }
    }

}
