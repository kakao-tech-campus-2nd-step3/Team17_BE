package homeTry.product.repository;

import homeTry.product.model.entity.ProductTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTagMappingRepository extends JpaRepository<ProductTagMapping, Long> {

    List<ProductTagMapping> findByProductTagIdIn(List<Long> tagIds);

    Optional<ProductTagMapping> findByProductId(Long productId);

}
