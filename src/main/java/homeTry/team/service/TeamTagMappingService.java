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

    //팀에 해당하는 태그를 TeamTagMapping 에 추가
    public void addTeamTagMappings(List<TeamTag> tagList, Team team) {
        tagList.forEach(
                tag -> teamTagMappingRepository.save(new TeamTagMapping(tag, team))
        );
    }

    //팀과 연관된 teamTagMapping 을 반환
    public List<TeamTagMapping> getTeamTagMappingsOfTeam(Team team) {
        return teamTagMappingRepository.findByTeam(team);
    }

    //해당 팀의 TeamTagMapping 모두 삭제
    public void deleteAllTeamTagMappingFromTeam(Team team) {
        teamTagMappingRepository.deleteByTeam(team);
    }

    //관리자에 의한 TeamTag 삭제로 인해 해당 TeamTagMapping deprecated화
    public void markDeprecated(TeamTag teamTag) {
        TeamTagMapping teamTagMapping = teamTagMappingRepository.findByTeamTag(teamTag);

        teamTagMapping.markAsDeprecated();
    }
}
