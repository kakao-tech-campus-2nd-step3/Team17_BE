package homeTry.team.service;

import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.exerciseList.service.ExerciseTimeService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import homeTry.tag.teamTag.dto.TeamTagDTO;
import homeTry.tag.teamTag.model.entity.TeamTag;
import homeTry.tag.teamTag.service.TeamTagService;
import homeTry.team.dto.RankingDTO;
import homeTry.team.dto.request.CheckingPasswordRequest;
import homeTry.team.dto.request.TeamCreateRequest;
import homeTry.team.dto.response.NewTeamFromResponse;
import homeTry.team.dto.response.RankingResponse;
import homeTry.team.dto.response.TeamResponse;
import homeTry.team.exception.*;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMember;
import homeTry.team.model.vo.Name;
import homeTry.team.repository.TeamRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service

public class TeamService {

    private static final int DEFAULT_PARTICIPANTS = 1;
    private static final int DEFAULT_RANKING = -1;
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final TeamTagService teamTagService;
    private final TeamTagMappingService teamTagMappingService;
    private final TeamMemberService teamMemberService;
    private final ExerciseHistoryService exerciseHistoryService;
    private final ExerciseTimeService exerciseTimeService;

    public TeamService(TeamRepository teamRepository,
                       MemberService memberService,
                       TeamTagService teamTagService,
                       TeamTagMappingService teamTagMappingService,
                       TeamMemberService teamMemberService,
                       ExerciseHistoryService exerciseHistoryService,
                       ExerciseTimeService exerciseTimeService) {
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.teamTagService = teamTagService;
        this.teamTagMappingService = teamTagMappingService;
        this.teamMemberService = teamMemberService;
        this.exerciseHistoryService = exerciseHistoryService;
        this.exerciseTimeService = exerciseTimeService;
    }

    //팀 추가 기능
    @Transactional
    public void addTeam(MemberDTO memberDTO, TeamCreateRequest teamCreateRequest) {
        validateTeamName(teamCreateRequest); // 팀 이름 유효성 검사 수행

        Member leader = memberService.getMemberEntity(memberDTO.id());

        Team team = teamRepository.save(createTeam(teamCreateRequest, leader)); //팀 저장

        teamMemberService.addTeamMember(team, leader); //리더를 TeamMember 엔티티에 추가

        addTagsToTeam(teamCreateRequest.tagIdList(), team); //팀에 태그 정보 추가
    }

    private Team createTeam(TeamCreateRequest teamCreateRequest, Member leader) {
        return new Team(
                teamCreateRequest.teamName(),
                teamCreateRequest.teamDescription(),
                leader,
                teamCreateRequest.maxParticipants(),
                DEFAULT_PARTICIPANTS,
                teamCreateRequest.password()
        );
    }

    //동일한 이름을 가지고 있는지 체크
    private void validateTeamName(TeamCreateRequest teamCreateRequest) {
        teamRepository.findByTeamName(new Name(teamCreateRequest.teamName()))
                .ifPresent(team -> {
                    throw new TeamNameAlreadyExistsException();
                });
    }

    //팀의 태그 정보를 추가
    private void addTagsToTeam(List<TeamTagDTO> teamTagDTOList, Team team) {
        List<Long> tagIdList = teamTagDTOList
                .stream()
                .map(TeamTagDTO::tagId)
                .toList();

        List<TeamTag> tagList = teamTagService.getTeamTagList(tagIdList);

        teamTagMappingService.addTeamTagMappings(tagList, team);
    }

    //팀 삭제 기능
    @Transactional
    public void deleteTeam(MemberDTO memberDTO, Long teamId) {
        Member member = memberService.getMemberEntity(memberDTO.id());

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException());

        validateIsLeader(team.getLeader(), member); //팀 리더인지 체크

        teamMemberService.deleteAllTeamMemberFromTeam(team); // 해당 팀에 대한 TeamMember 데이터 삭제

        teamTagMappingService.deleteAllTeamTagMappingFromTeam(team); //해당 팀에 대한 TeamTag 데이터 삭제

        teamRepository.delete(team); //Team 삭제
    }

    //팀 리더인지 체크
    private void validateIsLeader(Member leader, Member member) {
        if (leader != member) {
            throw new NotTeamLeaderException();
        }
    }


    //전체 팀 조회 (페이징 적용)
    @Transactional(readOnly = true)
    public Slice<TeamResponse> getTotalTeamSlice(MemberDTO memberDTO, Pageable pageable) {
        Member member = memberService.getMemberEntity(memberDTO.id());

        Slice<Team> teamListSlice = teamRepository.findTeamExcludingMember(member, pageable);

        //TeamResponse 로 변환
        List<TeamResponse> teamResponseList = getTeamResponseList(teamListSlice.getContent());

        return new SliceImpl<>(teamResponseList, pageable, teamListSlice.hasNext());
    }

    //팀 리스트를 TeamResponse 리스트로 변환
    private List<TeamResponse> getTeamResponseList(List<Team> teamList) {
        return teamList
                .stream()
                .map(this::convertToTeamResponse)
                .toList();
    }

    //개별 팀에 대해서 응답을 위한 TeamResponse 로 변환
    private TeamResponse convertToTeamResponse(Team team) {
        List<TeamTagDTO> tagList = teamTagService.getTeamTagsOfTeam(team);
        return TeamResponse.of(team, tagList);

    }

    //새로운 팀 생성에 필요한 정보 조회
    @Transactional(readOnly = true)
    public NewTeamFromResponse getNewTeamForm() {
        List<TeamTagDTO> teamTagDTOList = teamTagService.getAllTeamTagList();
        return new NewTeamFromResponse(teamTagDTOList);
    }

    //태그 처리 된 팀 리스트 조회 기능(페이징 적용)
    @Transactional(readOnly = true)
    public Slice<TeamResponse> getTaggedTeamList(Pageable pageable, MemberDTO memberDTO, List<Long> tagIdList) {
        Member member = memberService.getMemberEntity(memberDTO.id());

        List<TeamTag> tagList = teamTagService.getTeamTagList(tagIdList);

        long tagListSize = tagList.size();

        Slice<Team> teamListSlice = teamRepository.findTaggedTeamExcludingMember(tagList, tagListSize, member, pageable);

        List<TeamResponse> teamResponseList = getTeamResponseList(teamListSlice.getContent());

        return new SliceImpl<>(teamResponseList, pageable, teamListSlice.hasNext());
    }

    //팀 랭킹 조회 기능(페이징 적용)
    @Transactional(readOnly = true)
    public RankingResponse getTeamRanking(MemberDTO memberDTO, Long teamId, Pageable pageable, LocalDate date) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException());

        List<Member> memberList = getMemberList(team); //팀의 멤버들을 조회해옴

        List<RankingDTO> rankingList = getRankingList(memberList, date); //랭킹을 구해옴

        RankingDTO myRanking = rankingList //내 랭킹을 찾음
                .stream()
                .filter(rankingDTO -> memberDTO.nickname().equals(rankingDTO.name()))
                .findFirst()
                .orElseThrow(() -> new MyRankingNotFoundException());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), rankingList.size());
        List<RankingDTO> PagedRankingList = rankingList.subList(start, end); //페이징 처리를 위해 리스트 슬라이싱

        boolean hasNext = end < rankingList.size();

        Slice<RankingDTO> slice = new SliceImpl<>(rankingList, pageable, hasNext); // 랭킹 페이지 생성

        return new RankingResponse(myRanking.ranking(), myRanking.name(), myRanking.totalExerciseTime(), slice);
    }

    //팀의 멤버를 찾아와주는 기능
    private List<Member> getMemberList(Team team) {
        List<TeamMember> teamMemberList = teamMemberService.getTeamMember(team);

        return teamMemberList // 해당 팀의 멤버 리스트를 받음
                .stream()
                .map(TeamMember::getMember)
                .toList();
    }

    //멤버 리스트에서 랭킹을 매겨주는 기능
    private List<RankingDTO> getRankingList(List<Member> memberList, LocalDate date) {
        List<RankingDTO> totalExerciseTimeList = new ArrayList<>();
        if (date.isEqual(LocalDate.now())) // 오늘 조회인경우
            totalExerciseTimeList = getTotalExerciseTimeListOfToday(memberList);
        if (!date.isEqual(LocalDate.now())) // 과거 조회인경우
            totalExerciseTimeList = getTotalExerciseTimeListOfHistory(memberList, date);

        return totalExerciseTimeList //멤버들 랭킹 구함
                .stream()
                .sorted(Comparator.comparing(RankingDTO::totalExerciseTime).reversed())
                .map(this::autoIncrementRankig)
                .toList();
    }

    private RankingDTO autoIncrementRankig(RankingDTO rankingDTO) {
        return new RankingDTO(rankingDTO.name(), rankingDTO.ranking() + 1, rankingDTO.totalExerciseTime());
    }

    //멤버들의 오늘 totalExerciseTime 을 조회
    private List<RankingDTO> getTotalExerciseTimeListOfToday(List<Member> memberList) {
        return memberList
                .stream()
                .map(member -> {
                    Long totalExerciseTime = exerciseTimeService.getExerciseTimesForToday(member.getId());
                    return RankingDTO.of(member.getNickname(), DEFAULT_RANKING, totalExerciseTime);
                })
                .toList();
    }

    //멤버들의 과거 특정 날에 대한 totalExerciseTime 을 조회
    private List<RankingDTO> getTotalExerciseTimeListOfHistory(List<Member> memberList, LocalDate date) {
        return memberList
                .stream()
                .map(member -> {
                    Long totalExerciseTime = exerciseHistoryService.getExerciseHistoriesForDay(member.getId(), date);
                    return RankingDTO.of(member.getNickname(), DEFAULT_RANKING, totalExerciseTime);
                })
                .toList();
    }

    //멤버가 팀에 가입
    @Transactional
    public void joinTeam(MemberDTO memberDTO, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException());

        Member member = memberService.getMemberEntity(memberDTO.id());

        teamMemberService.addTeamMember(team, member); // 팀에 가입
    }

    //멤버가 팀에서 탈퇴
    @Transactional
    public void withDrawTeam(MemberDTO memberDTO, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException());

        Member member = memberService.getMemberEntity(memberDTO.id());

        teamMemberService.deleteTeamMember(team, member);
    }

    //팀 비밀번호 검사
    @Transactional(readOnly = true)
    public void checkPassword(Long teamId, CheckingPasswordRequest checkingPasswordRequest) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException());

        verifyPassword(team, checkingPasswordRequest);
    }

    //팀 비밀번호와 맞는 지 검사 수행
    private void verifyPassword(Team team, CheckingPasswordRequest checkingPasswordRequest) {
        boolean result = team.getPassword()
                .map(password -> password.getValue().equals(checkingPasswordRequest.password()))
                .orElseThrow(() -> new TeamHasNotPasswordException());

        if (!result) {
            throw new InvalidPasswordException();
        }
    }
}


