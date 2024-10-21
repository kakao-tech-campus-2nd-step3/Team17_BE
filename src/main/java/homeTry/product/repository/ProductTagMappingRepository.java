package homeTry.product.repository;

import homeTry.product.model.entity.ProductTagMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagMappingRepository extends JpaRepository<ProductTagMapping, Long> {

    @Query("SELECT ptm.productId FROM ProductTagMapping ptm WHERE ptm.productTagId IN :tagIds")
    List<Long> findProductIdsByTagIds(List<Long> tagIds);

}
