package homeTry.product.repository;

import homeTry.product.model.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 태그 X, 조회수 내림차순, 가격 오름차순 - 상품 조회
    Slice<Product> findAllByOrderByViewCountDescPriceAsc(Pageable pageable);

    // 태그 O, 조회수 내림차수, 가격 오름차순 - 특정 상품 조회
    Slice<Product> findByIdInOrderByViewCountDescPriceAsc(List<Long> ids, Pageable pageable);

    // 관리자 페이지 상품 조회
    Page<Product> findAllByOrderByIdAsc(Pageable pageable);

}
