package homeTry.team.service;

import homeTry.tag.model.entity.Tag;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTag;
import homeTry.team.repository.TeamTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamTagService {

    private final TeamTagRepository teamTagRepository;

    public TeamTagService(TeamTagRepository teamTagRepository) {
        this.teamTagRepository = teamTagRepository;
    }

    //팀에 해당하는 태그를 TeamTag에 추가
    //TeamService의 addTeam 메소드에서 하나의 트랜잭션으로 묶여있음
    public void addTeamTags(List<Tag> tagList, Team team) {
        tagList.forEach(
                tag -> teamTagRepository.save(new TeamTag(tag, team)));
    }

    //팀과 연관된 teamTag를 반환
    public List<TeamTag> getTeamTagsOfTeam(Team team) {
        return teamTagRepository.findByTeam(team);
    }

    //해당 팀의 TeamTag 모두 삭제
    public void deleteAllTeamTagFromTeam(Team team) {
        teamTagRepository.deleteByTeam(team);
    }
}
