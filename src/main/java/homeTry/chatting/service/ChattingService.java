package homeTry.chatting.service;

import homeTry.chatting.dto.request.ChattingMessageRequest;
import homeTry.chatting.dto.response.ChattingMessageResponse;
import homeTry.chatting.exception.badRequestException.InvalidTeamIdException;
import homeTry.chatting.repository.ChattingRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.team.exception.badRequestException.TeamMemberNotFoundException;
import homeTry.team.model.entity.TeamMemberMapping;
import homeTry.team.service.TeamMemberMappingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChattingService {

    private final TeamMemberMappingService teamMemberMappingService;
    private final ChattingRepository chattingRepository;


    public ChattingService(ChattingRepository chattingRepository, TeamMemberMappingService teamMemberMappingService) {
        this.chattingRepository = chattingRepository;
        this.teamMemberMappingService = teamMemberMappingService;
    }

    @Transactional
    public ChattingMessageResponse saveChattingMessage(Long teamId,
            ChattingMessageRequest chattingMessageRequest,
            MemberDTO memberDTO) {

        TeamMemberMapping teamMemberMapping;

        try {
            teamMemberMapping = teamMemberMappingService.getTeamMemberMappingById(teamId, memberDTO.id());
        } catch (TeamMemberNotFoundException e) {
            throw new InvalidTeamIdException();
        }

        return ChattingMessageResponse.from(
                chattingRepository.save(chattingMessageRequest.toEntity(teamMemberMapping)));
    }

    @Transactional(readOnly = true)
    public Slice<ChattingMessageResponse> getChattingMessageSlice(Long teamId,
            MemberDTO memberDTO, Pageable pageable) {

        //잘못된 teamId 방지
        try {
            teamMemberMappingService.getTeamMemberMappingById(teamId, memberDTO.id());
        } catch (TeamMemberNotFoundException e) {
            throw new InvalidTeamIdException();
        }

        return chattingRepository.findByTeamMemberMappingTeamId(teamId, pageable)
                .map(ChattingMessageResponse::from);
    }

    @Transactional
    public void deleteChattingMessageAll(Long teamId) {
        chattingRepository.deleteAllByTeamMemberMappingTeamId(teamId);
    }
}