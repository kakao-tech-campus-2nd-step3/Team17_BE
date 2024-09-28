package homeTry.team.service;

import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import homeTry.team.dto.RequestTeamDTO;
import homeTry.team.dto.ResponseRanking;
import homeTry.team.model.entity.Team;
import homeTry.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
