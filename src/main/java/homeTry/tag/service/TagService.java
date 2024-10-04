package homeTry.tag.service;

import homeTry.tag.dto.TagDTO;
import homeTry.tag.model.entity.Tag;
import homeTry.tag.repository.TagRepository;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamTag;
import homeTry.team.service.TeamTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TeamTagService teamTagService;

    public TagService(TagRepository tagRepository, TeamTagService teamTagService) {
        this.tagRepository = tagRepository;
        this.teamTagService = teamTagService;
    }

    //모든 태그 반환
    @Transactional(readOnly = true)
    public List<TagDTO> getAllTagList () {
        List<Tag> tagList = tagRepository.findAll();
        return tagList.stream()
                .map(TagDTO::of)
                .toList();
    }

    //팀이 가지고 있는 tag를 찾아서 tagDTO로 반환
    @Transactional(readOnly = true)
    public List<TagDTO> getTagsForTeam(Team team) {
        List<TeamTag> teamTagList = teamTagService.getTeamTagsByTeam(team);
        return teamTagList.stream()
                .map(teamTag -> TagDTO.of(teamTag.getTag()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Tag> getTagEntityList(List<Long> tagIdList){
        return tagIdList
                .stream()
                .map(tagId-> tagRepository.findById(tagId)
                        .orElseThrow(()-> new IllegalArgumentException("해당 태그ID를 가진 태그가 존재하지 않습니다")))
                .toList();
    }

}
