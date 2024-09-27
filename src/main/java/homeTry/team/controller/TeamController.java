package homeTry.team.controller;

import homeTry.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.team.dto.RequestTeamDTO;
import homeTry.team.service.TeamService;
import jakarta.validation.Valid;
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

    @PostMapping()
    public ResponseEntity<Void> addTeam (@LoginMember MemberDTO memberDTO, @Valid RequestTeamDTO requestTeamDTO){
        teamService.addTeam(memberDTO, requestTeamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{teamId)")
    public ResponseEntity<Void> deleteTeam(@LoginMember MemberDTO memberDTO,
                                           @PathVariable("teamId") Long teamId){
        teamService.deleteTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}