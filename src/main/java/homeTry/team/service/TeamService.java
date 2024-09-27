package homeTry.team.service;

import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import homeTry.team.dto.RequestTeamDTO;
import homeTry.team.model.entity.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private static final int DEFAULT_PARTICIPANTS = 1;

    public TeamService(TeamRepository teamRepository, MemberService memberService) {
        this.teamRepository = teamRepository;
        this.memberService = memberService;
    }

    @Transactional
    public void addTeam(MemberDTO memberDTO, RequestTeamDTO requestTeamDTO) {
        Member member = memberService.getMember(memberDTO.email());
        Team team = teamRepository.save(new Team(
                requestTeamDTO.teamName(),
                requestTeamDTO.teamDescription(),
                member.getId(),
                requestTeamDTO.maxParticipants(),
                DEFAULT_PARTICIPANTS,
                requestTeamDTO.password()
        ));
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Member
    }
}
