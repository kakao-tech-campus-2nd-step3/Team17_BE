package homeTry.team.service;

import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import homeTry.team.dto.RequestTeamDTO;
import homeTry.team.dto.ResponseRanking;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMember;
import homeTry.team.repository.TeamMemberRepository;
import homeTry.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final TeamMemberRepository teamMemberRepository;
    private static final int DEFAULT_PARTICIPANTS = 1;

    public TeamService(TeamRepository teamRepository, MemberService memberService, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Transactional
    public void addTeam(MemberDTO memberDTO, RequestTeamDTO requestTeamDTO) {
        Member leader = memberService.getMemberEntity(memberDTO.id());
        Team team = teamRepository.save(new Team(
                requestTeamDTO.teamName(),
                requestTeamDTO.teamDescription(),
                leader,
                requestTeamDTO.maxParticipants(),
                DEFAULT_PARTICIPANTS,
                requestTeamDTO.password()
        ));
    }

    @Transactional
    public void deleteTeam(MemberDTO memberDTO, Long teamId) {
        Member leader = memberService.getMemberEntity(memberDTO.id());

        Team team  = teamRepository.findById(teamId)
                .orElseThrow(()->new IllegalArgumentException("찾을 수 없습니다"));

        List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);

        teamMembers.stream() //해당 Team의 TeamMember 데이터 모두 삭제
                .forEach(teamMemberRepository::delete);

        teamRepository.delete(team); //Team 삭제
    }

    /*
    //Team과 member간의 ERD 다이어그램 수정 후 구현
    @Transactional(readOnly = true)
    public Page<ResponseRanking> getRanking(MemberDTO memberDTO, Pageable pageable, int year, int month, int day) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = LocalDate.of(year, month, day);
        if(targetDate.isBefore(currentDate))  // 현재 이전의 날짜에 대한 랭킹 요청 시
            return getTodayRanking(pageable, currentDate, memberDTO);



    }
    private Page<ResponseRanking> getTodayRanking(Pageable pageable, LocalDate currentDate, MemberDTO memberDTO) {
        memberDTO
    }
    */

}
