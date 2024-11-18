package homeTry.product.service;

import homeTry.product.dto.response.ProductResponse;
import homeTry.product.exception.badRequestException.ProductNotFoundException;
import homeTry.product.model.entity.Product;
import homeTry.product.repository.ProductRepository;
import homeTry.tag.productTag.dto.ProductTagDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTagMappingService productTagMappingService;

    public ProductService(ProductRepository productRepository,
        ProductTagMappingService productTagMappingService) {
        this.productRepository = productRepository;
        this.productTagMappingService = productTagMappingService;
    }

    @Transactional(readOnly = true)
    public Slice<ProductResponse> getProducts(List<Long> tagIds, Pageable pageable) {

        // tag O -> 해당 태그에 맞는 상품들을 1. 조회수 내림차순 2. 가격 오름차순으로 정렬
        // tag X -> 전체 상품을 1. 조회수 내림차순 2. 가격 오름차순으로 정렬
        Slice<Product> products = (tagIds != null && !tagIds.isEmpty())
            ? getProductsByTagIds(tagIds, pageable)
            : productRepository.findAllByIsDeprecatedFalseOrderByViewCountDescPriceAsc(pageable);

        return products.map(product -> {
            ProductTagDto tagDto = productTagMappingService.getTagForProduct(product.getId());
            return ProductResponse.from(product, tagDto);
        });
    }

    private Slice<Product> getProductsByTagIds(List<Long> tagIds, Pageable pageable) {
        List<Long> productIds = productTagMappingService.getProductIdsByTagIds(tagIds);
        return productRepository.findByIdInAndIsDeprecatedFalseOrderByViewCountDescPriceAsc(productIds, pageable);
    }

    // 특정 상품 선택 시 해당 상품의 조회 수 증가
    @Transactional
    public void incrementViewCount(Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }

        productRepository.incrementViewCount(productId); // 조회수 증가 쿼리 호출
    }

}
