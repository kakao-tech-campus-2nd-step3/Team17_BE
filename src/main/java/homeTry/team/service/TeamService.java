package homeTry.team.service;

import homeTry.common.constants.DateTimeUtil;
import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.exerciseList.service.ExerciseTimeService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import homeTry.tag.teamTag.dto.AllTeamTagDTO;
import homeTry.tag.teamTag.dto.TeamTagDTO;
import homeTry.tag.teamTag.model.entity.TeamTag;
import homeTry.tag.teamTag.service.TeamTagService;
import homeTry.team.dto.RankingDTO;
import homeTry.team.dto.request.CheckingPasswordRequest;
import homeTry.team.dto.request.TeamCreateRequest;
import homeTry.team.dto.response.RankingResponse;
import homeTry.team.dto.response.TagListResponse;
import homeTry.team.dto.response.TeamResponse;
import homeTry.team.exception.badRequestException.*;
import homeTry.team.model.entity.Team;
import homeTry.team.model.vo.Name;
import homeTry.team.repository.TeamRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service

public class TeamService {

    private static final int DEFAULT_PARTICIPANTS = 1;
    private static final int DEFAULT_RANKING = 0;
    private static final int ZERO = 0;

    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final TeamTagService teamTagService;
    private final TeamTagMappingService teamTagMappingService;
    private final TeamMemberMappingService teamMemberMappingService;
    private final ExerciseHistoryService exerciseHistoryService;
    private final ExerciseTimeService exerciseTimeService;


    public TeamService(TeamRepository teamRepository,
                       MemberService memberService,
                       TeamTagService teamTagService,
                       TeamTagMappingService teamTagMappingService,
                       TeamMemberMappingService teamMemberMappingService,
                       ExerciseHistoryService exerciseHistoryService,
                       ExerciseTimeService exerciseTimeService) {
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.teamTagService = teamTagService;
        this.teamTagMappingService = teamTagMappingService;
        this.teamMemberMappingService = teamMemberMappingService;
        this.exerciseHistoryService = exerciseHistoryService;
        this.exerciseTimeService = exerciseTimeService;
    }

    //팀 추가 기능
    @Transactional
    public void addTeam(MemberDTO memberDTO, TeamCreateRequest teamCreateRequest) {
        validateTeamName(teamCreateRequest); // 팀 이름 유효성 검사 수행

        Member leader = memberService.getMemberEntity(memberDTO.id());

        Team team = teamRepository.save(createTeam(teamCreateRequest, leader)); //팀 저장

        teamMemberMappingService.addTeamMember(team, leader); //리더를 TeamMemberMapping 엔티티에 추가

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
    private void addTagsToTeam(List<Long> tagIdList, Team team) {
        List<TeamTag> tagList = teamTagService.getTeamTagList(tagIdList);

        teamTagMappingService.addTeamTagMappings(tagList, team);
    }

    //팀 조회 기능
    @Transactional(readOnly = true)
    public Slice<TeamResponse> getSearchedTeamList(Pageable pageable, List<Long> tagIdList, String teamName, MemberDTO memberDTO) {

        final boolean HAS_TAGS = tagIdList != null;
        final boolean HAS_NAMES = teamName != null;

        Member member = memberService.getMemberEntity(memberDTO.id());

        // 팀 이름과 태그 리스트가 주어진 경우
        if (HAS_TAGS && HAS_NAMES)
            return getTeamsByTagsAndName(pageable, member, tagIdList, teamName);

        //태그 리스트만 주어진 경우
        if (HAS_TAGS)
            return getTeamsByTags(pageable, member, tagIdList);

        // 팀 이름만 주어진 경우
        if (HAS_NAMES)
            return getTeamsByName(pageable, member, teamName);

        //그룹 화면 최초 접속인 경우
        return getTeams(pageable, member);

    }

    //팀 이름과 태그 리스트가 적용된 팀을 조회하는 기능
    private Slice<TeamResponse> getTeamsByTagsAndName(Pageable pageable, Member member, List<Long> tagIdList, String teamName) {

        List<TeamTag> tagList = teamTagService.getTeamTagList(tagIdList);

        long tagListSize = tagList.size();

        Slice<Team> teamListSlice = teamRepository.findByTeamNameAndTagListExcludingMember(tagList, tagListSize, member, pageable, teamName);

        List<TeamResponse> teamResponseList = getTeamResponseList(teamListSlice.getContent());

        return new SliceImpl<>(teamResponseList, pageable, teamListSlice.hasNext());
    }

    // 태그 리스트가 적용된 팀을 조회하는 기능
    private Slice<TeamResponse> getTeamsByTags(Pageable pageable, Member member, List<Long> tagIdList) {

        List<TeamTag> tagList = teamTagService.getTeamTagList(tagIdList);

        long tagListSize = tagList.size();

        Slice<Team> teamListSlice = teamRepository.findTaggedTeamExcludingMember(tagList, tagListSize, member, pageable);

        List<TeamResponse> teamResponseList = getTeamResponseList(teamListSlice.getContent());

        return new SliceImpl<>(teamResponseList, pageable, teamListSlice.hasNext());
    }

    //팀 이름으로 팀을 조회하는 기능
    private Slice<TeamResponse> getTeamsByName(Pageable pageable, Member member, String teamName) {

        Slice<Team> teamListSlice = teamRepository.findByTeamNameExcludingMember(teamName, member, pageable);

        List<TeamResponse> teamResponseList = getTeamResponseList(teamListSlice.getContent());

        return new SliceImpl<>(teamResponseList, pageable, teamListSlice.hasNext());
    }

    //그룹 화면 최초 접속인 경우
    private Slice<TeamResponse> getTeams(Pageable pageable, Member member) {

        Slice<Team> teamListSlice = teamRepository.findTeamExcludingMember(member, pageable);

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
        Member leader = memberService.getMemberEntity(team.getLeaderId());
        return TeamResponse.of(team, tagList, leader.getNickname());
    }

    //모든 팀 태그를 조회
    @Transactional(readOnly = true)
    public TagListResponse getAllTeamTagList(MemberDTO memberDTO) {
        AllTeamTagDTO allTeamTagDTO = teamTagService.getAllTeamTagList(); //모든 태그 조회해 옴

        return TagListResponse.of(allTeamTagDTO);
    }

    //팀 랭킹 조회 기능(페이징 적용)
    @Transactional(readOnly = true)
    public RankingResponse getTeamRanking(MemberDTO memberDTO, Long teamId, Pageable pageable, LocalDate date) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        Member member = memberService.getMemberEntity(memberDTO.id());

        isMemberOfTeam(member, team); //유저가 팀에 속해있는지 확인

        List<Member> memberList = teamMemberMappingService.getMemberListByTeam(team); //팀의 멤버들을 조회해옴

        List<RankingDTO> rankingList = getRankingList(memberList, date); //랭킹을 구해옴

        RankingDTO myRanking = findMyRanking(rankingList, memberDTO.nickname()); //내 랭킹을 찾음

        Slice<RankingDTO> slice = getSlice(rankingList, pageable); // 슬라이싱 처리

        return new RankingResponse(myRanking.ranking(), myRanking.name(), myRanking.totalExerciseTime(), slice);
    }

    //유저가 해당 팀에 속해있는 멤버인지 조회
    private void isMemberOfTeam(Member member, Team team) {
        teamMemberMappingService.getTeamMemberMapping(team, member);
    }

    //리스트에 대해서 슬라이싱 처리해 주는 제네릭 메소드
    private <T> Slice<T> getSlice(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<T> pagedList = list.subList(start, end); //페이징 처리를 위해 리스트 슬라이싱

        boolean hasNext = end < list.size();

        return new SliceImpl<>(list, pageable, hasNext);
    }

    private RankingDTO findMyRanking(List<RankingDTO> rankingList, String userNickname) {
        return rankingList
                .stream()
                .filter(rankingDTO -> userNickname.equals(rankingDTO.name()))
                .findFirst()
                .orElse(new RankingDTO(
                        userNickname,
                        DEFAULT_RANKING,
                        0L
                ));
    }

    //멤버 리스트에서 랭킹을 매겨주는 기능
    private List<RankingDTO> getRankingList(List<Member> memberList, LocalDate date) {
        List<RankingDTO> totalExerciseTimeList = new ArrayList<>(); //멤버들의 totalExerciseTime을 저장하는 리스트

        LocalDate currentDate = DateTimeUtil.getAdjustedCurrentDate(); //현재 날짜값 받아옴

        if (date.isEqual(currentDate)) // 오늘 조회인경우
            totalExerciseTimeList = getTotalExerciseTimeListOfToday(memberList);
        if (!date.isEqual(currentDate)) // 과거 조회인경우
            totalExerciseTimeList = getTotalExerciseTimeListOfHistory(memberList, date);

        AtomicLong previousTotalExerciseTime = new AtomicLong(-1); //내 앞 순위의 totalExerciseTime을 저장
        AtomicInteger rankCounter = new AtomicInteger(ZERO); //초기 순위를 0으로 지정

        List<RankingDTO> rankingDTOList = totalExerciseTimeList // 시간기준 내림차순으로 정렬
                .stream()
                .sorted(Comparator.comparing(RankingDTO::totalExerciseTime).reversed())
                .map(rankingDTO -> {
                    if (rankingDTO.totalExerciseTime() != previousTotalExerciseTime.get()) { //앞 순위와 나의 totalExerciseTime 시간이 다른 경우
                        rankCounter.getAndIncrement();
                        previousTotalExerciseTime.set(rankingDTO.totalExerciseTime());
                    }
                    return new RankingDTO(rankingDTO.name(), rankCounter.get(), rankingDTO.totalExerciseTime());
                })
                .toList();

        return rankingDTOList;
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
                .orElseThrow(TeamNotFoundException::new);

        Member member = memberService.getMemberEntity(memberDTO.id());

        team.joinTeam(); // 팀에 가입

        teamMemberMappingService.addTeamMember(team, member); // 매핑 정보 추가
    }

    //팀 비밀번호 검사
    @Transactional(readOnly = true)
    public void checkPassword(Long teamId, CheckingPasswordRequest checkingPasswordRequest) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        team.verifyPassword(checkingPasswordRequest.password());
    }

    //Team엔티티 반환
    @Transactional(readOnly = true)
    public Team getTeamEntity(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
    }

    //내가 가입한 팀 리스트 반환 (슬라이스 적용)
    @Transactional(readOnly = true)
    public Slice<TeamResponse> getMyTeamList(MemberDTO memberDTO, Pageable pageable) {
        Member member = memberService.getMemberEntity(memberDTO.id());

        List<Team> teamList = teamMemberMappingService.getTeamListByMember(member);

        List<TeamResponse> myTeamList = teamList
                .stream()
                .map(this::convertToTeamResponse)
                .toList();

        return getSlice(myTeamList, pageable);
    }

    public void deleteTeam(Long memberId, Team team) {
        Member member = memberService.getMemberEntity(memberId);

        if (!team.validateIsLeader(memberId)) //팀 리더인지 체크
            throw new NotTeamLeaderException();

        teamMemberMappingService.deleteAllTeamMemberFromTeam(team); // 해당 팀에 대한 TeamMemberMapping 데이터 삭제

        teamTagMappingService.deleteAllTeamTagMappingFromTeam(team); //해당 팀에 대한 TeamTagMapping 데이터 삭제

        teamRepository.delete(team); //Team 삭제
    }

    @Transactional
    public void withdrawTeam(Long memberId, Team team) {
        Member member = memberService.getMemberEntity(memberId);

        if (team.validateIsLeader(memberId)) { // 팀 리더가 탈퇴하는 요청인지 체크. 팀 리더는 팀 삭제만 가능. 탈퇴는 불가
            throw new TeamLeaderCannotWithdrawException();
        }

        teamMemberMappingService.markDeprecated(team, member); //TeamMemberMapping 테이블에서 softDelete

        team.decreaseParticipantsByWithdraw(); //팀의 현재 참여인원 감소
    }
}


