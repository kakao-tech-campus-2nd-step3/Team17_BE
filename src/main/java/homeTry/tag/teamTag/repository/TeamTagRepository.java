package homeTry.tag.teamTag.repository;

import homeTry.tag.model.vo.TagName;
import homeTry.tag.teamTag.model.entity.TeamTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {

    List<TeamTag> findAllByIsDeprecatedFalse();
    boolean existsByTagNameAndIsDeprecatedFalse(TagName tagName);

}
