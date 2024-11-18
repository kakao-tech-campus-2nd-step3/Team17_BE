package homeTry.tag.productTag.repository;

import homeTry.tag.model.vo.TagName;
import homeTry.tag.productTag.model.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long>{

    List<ProductTag> findAllByIsDeprecatedFalse();
    boolean existsByTagNameAndIsDeprecatedFalse(TagName tagName);

}