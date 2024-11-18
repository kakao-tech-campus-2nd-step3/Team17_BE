package homeTry.tag.model.entity;

import homeTry.common.entity.SoftDeletableEntity;
import homeTry.tag.model.vo.TagName;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Tag extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "tag_name", nullable = false, length = 15))
    private TagName tagName;

    protected Tag() {
    }

    public Tag(TagName tagName) {
        this.tagName = tagName;
    }

    public TagName getTagName() {
        return tagName;
    }

    public Long getId() {
        return id;
    }

    public void updateTagName(String tagName) {
        this.tagName = new TagName(tagName);
    }
}
