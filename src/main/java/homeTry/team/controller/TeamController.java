package homeTry.team.controller;

import homeTry.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.team.dto.RequestTeamDTO;
import homeTry.team.dto.ResponseRanking;
import homeTry.team.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController (TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Void> addTeam (@LoginMember MemberDTO memberDTO,
                                         @Valid RequestTeamDTO requestTeamDTO){
        teamService.addTeam(memberDTO, requestTeamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam (@LoginMember MemberDTO memberDTO,
                                            @PathVariable("teamId") Long teamID) {
        teamService.deleteTeam(memberDTO, teamID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
    //Team과 member간의 ERD 다이어그램 수정 후 구현
    @GetMapping("/{teamId}/ranking")
    public ResponseEntity<Page<ResponseRanking>> getRanking(@LoginMember MemberDTO memberDTO,
                                                            @PageableDefault(size = 10, sort = "exerciseTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                            @PathVariable("teamId") Long teamId,
                                                            @RequestParam("year") int year,
                                                            @RequestParam("month") int month,
                                                            @RequestParam("day") int day) {
        Page<ResponseRanking> rankingPage = teamService.getRanking(memberDTO, pageable, year, month, day);
        return ResponseEntity.ok(rankingPage);
    }
    */


    /*
    //태그 기능 제작 후 구현
    @GetMapping("/form")
    public ResponseEntity<ResponseTeamForm> (@LoginMember MemberDTO memberDTO){
    }
     */

    /*
    //Team과 member간의 ERD 다이어그램 수정 후 구현
    @DeleteMapping("/{teamId)")
    public ResponseEntity<Void> deleteTeam(@LoginMember MemberDTO memberDTO,
                                           @PathVariable("teamId") Long teamId){
        teamService.deleteTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    */
}