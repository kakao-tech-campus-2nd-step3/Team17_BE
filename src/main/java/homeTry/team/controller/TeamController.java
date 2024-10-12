package homeTry.team.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.team.dto.*;
import homeTry.team.dto.request.TeamCreateRequest;
import homeTry.team.dto.response.NewTeamFromResponse;
import homeTry.team.dto.response.RankingResponse;
import homeTry.team.dto.response.TeamResponse;
import homeTry.team.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    //팀 생성 api
    @PostMapping
    public ResponseEntity<Void> addTeam(@LoginMember MemberDTO memberDTO,
                                        @Valid @RequestBody TeamCreateRequest teamCreateRequest) {
        teamService.addTeam(memberDTO, teamCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팀 삭제 api
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@LoginMember MemberDTO memberDTO,
                                           @PathVariable("teamId") Long teamID) {
        teamService.deleteTeam(memberDTO, teamID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //모든 팀 조회 api (페이징 적용)
    @GetMapping
    public ResponseEntity<Page<TeamResponse>> getTotalTeamList(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @LoginMember MemberDTO memberDTO) {
        Page<TeamResponse> totalTeamPage = teamService.getTotalTeamPage(memberDTO, pageable);
        return ResponseEntity.ok(totalTeamPage);
    }

    //팀 생성 페이지에 필요한 정보 조회 api
    @GetMapping("/form")
    public ResponseEntity<NewTeamFromResponse> getNewTeamForm() {
        NewTeamFromResponse newTeamFromResponse = teamService.getNewTeamForm();
        return ResponseEntity.ok(newTeamFromResponse);
    }

    //태그를 통한 일부팀 조회 api (페이징 적용)
    @GetMapping("/tagged")
    public ResponseEntity<Page<TeamResponse>> getTaggedTeamList(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "tagIdList") List<Long> tagIdList,
            @LoginMember MemberDTO memberDTO) {
        Page<TeamResponse> taggedTeamPage = teamService.getTaggedTeamList(pageable, memberDTO, tagIdList);
        return ResponseEntity.ok(taggedTeamPage);
    }

    //팀 내 랭킹을 조회하는 api (페이징 적용)
    @GetMapping("/{teamId}/ranking")
    public ResponseEntity<RankingResponse> getTeamRanking(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId,
            @PageableDefault(size = 8, sort = "totalExerciseTime", direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute DateDTO dateDTO) {
        RankingResponse rankingPage = teamService.getTeamRanking(memberDTO, teamId, pageable, dateDTO);
        return ResponseEntity.ok(rankingPage);
    }

    // 팀에 가입
    @PostMapping("/join/{teamId}")
    public ResponseEntity<Void> joinTeam(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId) {
        teamService.joinTeam(memberDTO, teamId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팀에서 탈퇴
    @DeleteMapping("/withdraw/{teamId}")
    public ResponseEntity<Void> withdrawTeam(
            @LoginMember MemberDTO memberDTO,
            @PathVariable("teamId") Long teamId) {
        teamService.withDrawTeam(memberDTO, teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}