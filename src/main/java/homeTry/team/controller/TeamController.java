package homeTry.team.controller;

import homeTry.common.annotation.DateValid;
import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.team.dto.request.TeamCreateRequest;
import homeTry.team.dto.request.CheckingPasswordRequest;
import homeTry.team.dto.response.RankingResponse;
import homeTry.team.dto.response.TagListResponse;
import homeTry.team.dto.response.TeamResponse;
import homeTry.team.service.TeamService;
import homeTry.team.service.TeamJoinAndWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Team", description = "팀 관련 API")
@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamJoinAndWithdrawService teamJoinAndWithdrawService;

    public TeamController(TeamService teamService,
                          TeamJoinAndWithdrawService teamJoinAndWithdrawService) {
        this.teamService = teamService;
        this.teamJoinAndWithdrawService = teamJoinAndWithdrawService;
    }

    //팀 생성 api
    @PostMapping
    @Operation(summary = "팀 생성", description = "오청으로 들어온 데이터를 바탕으로 새로운 팀을 생성한다")
    @ApiResponse(responseCode = "201", description = "팀이 성공적으로 생성됌")
    public ResponseEntity<Void> addTeam(@LoginMember MemberDTO memberDTO,
                                        @Valid @RequestBody TeamCreateRequest teamCreateRequest) {
        teamService.addTeam(memberDTO, teamCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팀 삭제 api
    @DeleteMapping("/{teamId}")
    @Operation(summary = "팀 삭제", description = "팀장이 해당 팀을 삭제한다")
    @ApiResponse(responseCode = "204", description = "팀이 성공적으로 삭제됌")
    public ResponseEntity<Void> deleteTeam(@LoginMember MemberDTO memberDTO,
                                           @PathVariable("teamId") Long teamID) {
        teamJoinAndWithdrawService.deleteTeam(memberDTO.id(), teamID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //팀 조회 api (페이징 적용)
    @GetMapping
    @Operation(summary = "팀 조회", description = "태그Id리스트와 팀 이름을 바탕으로 팀을 조회한다 (페이징 적용)")
    @ApiResponse(responseCode = "200", description = "조회를 성공함")
    public ResponseEntity<Slice<TeamResponse>> searchingTeam(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "tagIdList", required = false) List<Long> tagIdList,
            @RequestParam(name = "teamName", required = false) String teamName,
            @LoginMember MemberDTO memberDTO) {
        Slice<TeamResponse> searchedTeamList = teamService.getSearchedTeamList(pageable, tagIdList, teamName, memberDTO);
        return ResponseEntity.ok(searchedTeamList);
    }

    //가지고 있는 모든 TeamTag를 반환 api
    @GetMapping("/teamTags")
    @Operation(summary = "모든 팀 태그 조회", description = "서비스에서 사용중인 모든 TeamTag를 조회한다")
    @ApiResponse(responseCode = "200", description = "조회를 성공함")
    public ResponseEntity<TagListResponse> getTeamTagList(
            @LoginMember MemberDTO memberDTO) {
        TagListResponse response = teamService.getAllTeamTagList(memberDTO);
        return ResponseEntity.ok(response);
    }

    //팀 내 랭킹을 조회하는 api (페이징 적용)
    @GetMapping("/{teamId}/ranking")
    @Operation(summary = "랭킹 조회", description = "해당 팀에 대한 팀원들의 랭킹과 내 랭킹을 조회한다 (페이징 적용)")
    @ApiResponse(responseCode = "200", description = "팀 내 랭킹을 조회하는데 성공함")
    public ResponseEntity<RankingResponse> getTeamRanking(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId,
            @PageableDefault(size = 8, sort = "totalExerciseTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "date") @DateValid @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
        RankingResponse rankingSlice = teamService.getTeamRanking(memberDTO, teamId, pageable, date);
        return ResponseEntity.ok(rankingSlice);
    }

    // 팀에 가입
    @PostMapping("/join/{teamId}")
    @Operation(summary = "팀 가입", description = "유저가 팀에 가입한다")
    @ApiResponse(responseCode = "201", description = "팀에 성공적으로 가입함")
    public ResponseEntity<Void> joinTeam(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId) {
        teamJoinAndWithdrawService.joinTeam(memberDTO, teamId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팀에서 탈퇴
    @DeleteMapping("/withdraw/{teamId}")
    @Operation(summary = "팀 탈퇴", description = "유저가 팀에서 탈퇴한다")
    @ApiResponse(responseCode = "204", description = "팀에서 성공적으로 탈퇴함")
    public ResponseEntity<Void> withdrawTeam(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId) {
        teamJoinAndWithdrawService.withdrawTeam(memberDTO.id(), teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //비밀번호 일치 검사 api
    @PostMapping("/checking/{teamId}")
    @Operation(summary = "팀 비밀번호 일치확인", description = "비공개 팀에 가입하기 위해 비밀번호가 일치한지 확인한다")
    @ApiResponse(responseCode = "200", description = "비밀번호와 일치함")
    public ResponseEntity<Void> verifyPassword(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId,
            @RequestBody @Valid CheckingPasswordRequest checkingPasswordRequest) {
        teamService.checkPassword(teamId, checkingPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //내가 가입한 팀 조회 (페이징 적용)
    @GetMapping("/joined")
    @Operation(summary = "가입한 팀 조회", description = "유저가 가입한 팀을 조회한다 (페이징 적용)")
    @ApiResponse(responseCode = "200", description = "가입한 팀 조회를 성공함")
    public ResponseEntity<Slice<TeamResponse>> getJoinedTeam(
            @LoginMember MemberDTO memberDTO,
            @PageableDefault(size = 8, sort = "teamId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Slice<TeamResponse> responseSlice = teamService.getMyTeamList(memberDTO, pageable);
        return ResponseEntity.ok(responseSlice);
    }
}