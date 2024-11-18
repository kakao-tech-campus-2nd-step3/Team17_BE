package homeTry.product.repository;

import homeTry.product.model.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 태그 X, 조회수 내림차순, 가격 오름차순 - 유효한(삭제되지 않은) 상품 조회
    Slice<Product> findAllByIsDeprecatedFalseOrderByViewCountDescPriceAsc(Pageable pageable);

    // 태그 O, 조회수 내림차수, 가격 오름차순 - 유효한(삭제되지 않은) 특정 상품 조회
    Slice<Product> findByIdInAndIsDeprecatedFalseOrderByViewCountDescPriceAsc(List<Long> ids, Pageable pageable);

    // 관리자 페이지 상품 조회
    @Query("SELECT p FROM Product p WHERE p.isDeprecated = false ORDER BY p.id ASC")
    Page<Product> findAllNonDeprecated(Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.viewCount = p.viewCount + 1 WHERE p.id = :productId")
    void incrementViewCount(@Param("productId") Long productId);

}
