package homeTry.tag.teamTag.model.entity;

import homeTry.tag.model.entity.Tag;
import homeTry.tag.model.vo.TagName;
import homeTry.tag.teamTag.model.vo.TeamTagAttribute;
import homeTry.team.model.vo.Name;
import jakarta.persistence.*;

@Entity
public class TeamTag extends Tag {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "tag_attribute", nullable = false))
    private TeamTagAttribute tagAttribute;

    protected TeamTag() {
        super();
    }

    public TeamTag(TagName tagName, TeamTagAttribute tagAttribute) {
        super(tagName);
        this.tagAttribute = tagAttribute;
    }

    public TeamTag(String tagName, String tagAttribute) {
        this(new TagName(tagName), new TeamTagAttribute(tagAttribute));
    }

    public TeamTagAttribute getTagAttribute() {
        return tagAttribute;
    }

    public void updateAttribute(String tagAttribute) {
        this.tagAttribute = new TeamTagAttribute(tagAttribute);
    }
}

