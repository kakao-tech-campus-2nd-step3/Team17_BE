package homeTry.team.controller;

import homeTry.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.team.dto.*;
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

    public TeamController (TeamService teamService) {
        this.teamService = teamService;
    }

    //팀 생성 api
    @PostMapping
    public ResponseEntity<Void> addTeam (@LoginMember MemberDTO memberDTO,
                                         @Valid @RequestBody RequestTeamDTO requestTeamDTO){
        teamService.addTeam(memberDTO, requestTeamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팀 삭제 api
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam (@LoginMember MemberDTO memberDTO,
                                            @PathVariable("teamId") Long teamID) {
        teamService.deleteTeam(memberDTO, teamID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //모든 팀 조회 api (페이징 적용)
    @GetMapping
    public ResponseEntity<Page<ResponseTeam>> getTotalTeamList(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ResponseTeam> totalTeamPage = teamService.getTotalTeamPage(pageable);
        return ResponseEntity.ok(totalTeamPage);
    }

    //팀 생성 페이지에 필요한 정보 조회 api
    @GetMapping
    @RequestMapping("/form")
    public ResponseEntity<ResponseNewTeamFrom> getNewTeamForm (){
        ResponseNewTeamFrom responseNewTeamFrom = teamService.getNewTeamForm();
        return ResponseEntity.ok(responseNewTeamFrom);
    }

    //태그를 통한 일부팀 조회 api (페이징 적용)
    @GetMapping("/tagged")
    public ResponseEntity<Page<ResponseTeam>> getTaggedTeamList (@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                           @RequestParam(name = "tagIdList")  List<Long> tagIdList) {
        Page<ResponseTeam> taggedTeamPage = teamService.getTaggedTeamList(pageable, tagIdList);
        return ResponseEntity.ok(taggedTeamPage);
    }

}