package homeTry.tag.model.entity;

import homeTry.team.model.vo.Name;
import jakarta.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name ="value",column = @Column(name = "tag_name", nullable = false, length = 15))
    private Name tagName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "tag_attribute", nullable = false))
    private Name tagAttribute;

    protected Tag () {}

    public Tag(Name tagName, Name tagAttribute) {
        this.tagName = tagName;
        this.tagAttribute = tagAttribute;
    }

    public Tag(String tagName, String tagAttribute) {
        this(new Name(tagName), new Name(tagAttribute));
    }

    public Name getTagAttribute() {
        return tagAttribute;
    }

    public Name getTagName() {
        return tagName;
    }

    public Long getId() {
        return id;
    }

    public void updateTagName(String tagName) {
        this.tagName = new Name(tagName);
    }

    public void updateAttribute(String tagAttribute) {
        this.tagAttribute = new Name(tagAttribute);
    }
}
