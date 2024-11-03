package homeTry.chatting.service;

import homeTry.chatting.dto.request.ChattingMessageRequest;
import homeTry.chatting.dto.response.ChattingMessageResponse;
import homeTry.chatting.exception.badRequestException.InvalidTeamIdException;
import homeTry.chatting.model.entity.Chatting;
import homeTry.chatting.repository.ChattingRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMember;
import homeTry.team.repository.TeamRepository;
import homeTry.team.service.TeamMemberService;
import homeTry.team.service.TeamService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChattingService {

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
    private final ChattingRepository chattingRepository;
    private final TeamRepository teamRepository;

    public ChattingService(TeamService teamService,
            TeamMemberService teamMemberService, ChattingRepository chattingRepository,
            TeamRepository teamRepository) {
        this.teamService = teamService;
        this.teamMemberService = teamMemberService;
        this.chattingRepository = chattingRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public ChattingMessageResponse saveChattingMessage(Long teamId,
            ChattingMessageRequest chattingMessageRequest,
            MemberDTO memberDTO) {

        //todo: teamService에서 get Team entity 구현하면 리팩터링 하기
        Team team = teamRepository.findById(teamId).orElseThrow(InvalidTeamIdException::new);

        List<TeamMember> teamMembers = teamMemberService.getTeamMember(team);

        // 해당 멤버 ID와 일치하는 TeamMember 객체 찾기
        TeamMember teamMember = teamMembers.stream()
                .filter(tm -> tm.getMember().getId().equals(memberDTO.id()))
                .findFirst()
                .orElseThrow(InvalidTeamIdException::new);

        return ChattingMessageResponse.from(
                chattingRepository.save(chattingMessageRequest.toEntity(teamMember)));
    }

    @Transactional(readOnly = true)
    public Slice<ChattingMessageResponse> getChattingMessageSlice(Long teamId,
            MemberDTO memberDTO, Pageable pageable) {
        Team team = teamRepository.findById(teamId).orElseThrow(InvalidTeamIdException::new);

        //잘못된 teamId 방지
        //todo : getTeamMember(team, member) 구현하기
        teamMemberService.getTeamMember(team).stream()
                .filter(tm -> tm.getMember().getId().equals(memberDTO.id()))
                .findFirst()
                .orElseThrow(InvalidTeamIdException::new);

        Slice<Chatting> chattingMessageSlice = chattingRepository.findByTeamMemberTeam(team,
                pageable);

        List<ChattingMessageResponse> chattingMessageResponseList = chattingMessageSlice.getContent()
                .stream()
                .map(ChattingMessageResponse::from)
                .toList();

        return new SliceImpl<>(chattingMessageResponseList, chattingMessageSlice.getPageable(),
                chattingMessageSlice.hasNext());

    }


}
