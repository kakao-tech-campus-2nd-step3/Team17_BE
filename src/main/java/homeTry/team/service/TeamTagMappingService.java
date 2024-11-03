package homeTry.team.service;

import homeTry.tag.teamTag.model.entity.TeamTag;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTagMapping;
import homeTry.team.repository.TeamTagMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamTagMappingService {

    private final TeamTagMappingRepository teamTagMappingRepository;

    public TeamTagMappingService(TeamTagMappingRepository teamTagMappingRepository) {
        this.teamTagMappingRepository = teamTagMappingRepository;
    }

    //팀에 해당하는 태그를 TeamTag에 추가
    //TeamService의 addTeam 메소드에서 하나의 트랜잭션으로 묶여있음
    public void addTeamTagMappings(List<TeamTag> tagList, Team team) {
        tagList.forEach(
                tag -> teamTagMappingRepository.save(new TeamTagMapping(tag, team)));
    }

    //팀과 연관된 teamTag를 반환
    public List<TeamTagMapping> getTeamTagMappingsOfTeam(Team team) {
        return teamTagMappingRepository.findByTeam(team);
    }

    //해당 팀의 TeamTag 모두 삭제
    public void deleteAllTeamTagMappingFromTeam(Team team) {
        teamTagMappingRepository.deleteByTeam(team);
    }
}
