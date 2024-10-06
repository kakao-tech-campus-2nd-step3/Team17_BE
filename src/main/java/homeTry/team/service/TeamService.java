package homeTry.team.service;

import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import homeTry.tag.dto.TagDTO;
import homeTry.tag.model.entity.Tag;
import homeTry.tag.service.TagService;
import homeTry.team.dto.*;
import homeTry.team.exception.NotTeamLeaderException;
import homeTry.team.exception.TeamNameAlreadyExistsException;
import homeTry.team.exception.TeamNotFoundException;
import homeTry.team.model.entity.Team;
import homeTry.team.model.vo.Name;
import homeTry.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final TagService tagService;
    private final TeamTagService teamTagService;
    private final TeamMemberService teamMemberService;
    private final ExerciseHistoryService exerciseHistoryService;
    private static final int DEFAULT_PARTICIPANTS = 1;

    public TeamService(TeamRepository teamRepository,
                       MemberService memberService,
                       TagService tagService,
                       TeamTagService teamTagService,
                       TeamMemberService teamMemberService,
                       ExerciseHistoryService exerciseHistoryService) {
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.tagService = tagService;
        this.teamTagService = teamTagService;
        this.teamMemberService = teamMemberService;
        this.exerciseHistoryService = exerciseHistoryService;
    }

    //팀 추가 기능
    @Transactional
    public void addTeam(MemberDTO memberDTO, RequestTeamDTO requestTeamDTO) {
        Member leader = memberService.getMemberEntity(memberDTO.id());

        validateTeamName(requestTeamDTO); // 팀 이름 유효성 검사 수행

        Team team = teamRepository.save(createTeam(requestTeamDTO, leader)); //팀 저장

        teamMemberService.addTeamMember(team, leader); //리더를 TeamMember 엔티티에 추가

        addTagsToTeam(requestTeamDTO.tagIdList(), team); //팀에 태그 정보 추가
    }

    private Team createTeam(RequestTeamDTO requestTeamDTO, Member leader) {
        return new Team(
                requestTeamDTO.teamName(),
                requestTeamDTO.teamDescription(),
                leader,
                requestTeamDTO.maxParticipants(),
                DEFAULT_PARTICIPANTS,
                requestTeamDTO.password()
        );
    }

    //동일한 이름을 가지고 있는지 체크
    private void validateTeamName(RequestTeamDTO requestTeamDTO) {
        teamRepository.findByTeamName(new Name(requestTeamDTO.teamName()))
                .ifPresent(team -> {
                    throw new TeamNameAlreadyExistsException();
                });
    }

    //팀의 태그 정보를 추가
    private void addTagsToTeam(List<TagDTO> tagDTOList, Team team) {
        List<Long> tagIdList = tagDTOList
                .stream()
                .map(TagDTO::tagId)
                .toList();

        List<Tag> tagList = tagService.getTagEntityList(tagIdList);

        teamTagService.addTeamTags(tagList, team);
    }

    //팀 삭제 기능
    @Transactional
    public void deleteTeam(MemberDTO memberDTO, Long teamId) {
        Member member = memberService.getMemberEntity(memberDTO.id());

        Team team  = teamRepository.findById(teamId)
                .orElseThrow(()-> new TeamNotFoundException());

        validateIsLeader(team.getLeader(), member); //팀 리더인지 체크

        teamMemberService.deleteAllTeamMemberFromTeam(team); // 해당 팀에 대한 TeamMember 데이터 삭제

        teamTagService.deleteAllTeamTagFromTeam(team); //해당 팀에 대한 TeamTag 데이터 삭제

        teamRepository.delete(team); //Team 삭제
    }

    //팀 리더인지 체크
    private void validateIsLeader(Member leader, Member member) {
        if (leader != member)
            throw new NotTeamLeaderException();
    }


    //전체 팀 조회 (페이징 적용)
    @Transactional(readOnly = true)
    public Page<ResponseTeam> getTotalTeamPage(Pageable pageable) {
        Page<Team> teamListPage = teamRepository.findAll(pageable);
        List<ResponseTeam> responseTeamList= teamListPage.getContent()
                .stream()
                .map(this::convertToResponseTeam)
                .toList();

        return new PageImpl<>(responseTeamList, pageable, teamListPage.getTotalElements());
    }

    //개별 팀에 대해서 응답을 위한 ResponseTeam으로 변환
    private ResponseTeam convertToResponseTeam(Team team) {
        List<TagDTO> tagList = tagService.getTagsForTeam(team);
        return ResponseTeam.of(team, tagList);

    }


    //새로운 팀 생성에 필요한 정보 조회
    @Transactional(readOnly = true)
    public ResponseNewTeamFrom getNewTeamForm() {
        List<TagDTO> tagDTOList = tagService.getAllTagList();
        return new ResponseNewTeamFrom(tagDTOList);
    }

    //태그 처리 된 팀 리스트 조회 기능(페이징 적용)
    @Transactional(readOnly = true)
    public Page<ResponseTeam> getTaggedTeamList(Pageable pageable, List<Long> tagIdList) {
        List<Tag> tagList = tagService.getTagEntityList(tagIdList);

        long tagListSize = tagList.size();

        Page<Team> taggedTeamListPage = teamTagService.getTaggedTeamTagList(tagList, tagListSize, pageable);

        List<Team> taggedTeamList= taggedTeamListPage.getContent();

        List<ResponseTeam> responseTeamList = taggedTeamList
                .stream()
                .map(this::convertToResponseTeam)
                .toList();

        return new PageImpl<>(responseTeamList, pageable,taggedTeamListPage.getTotalElements());
    }

}
