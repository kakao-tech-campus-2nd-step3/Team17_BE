package homeTry.product.service;

import homeTry.member.dto.MemberDTO;
import homeTry.product.dto.response.ProductResponse;
import homeTry.product.exception.badRequestException.InvalidMemberException;
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
    public Slice<ProductResponse> getProducts(List<Long> tagIds, MemberDTO memberDTO,
        Pageable pageable) {

        if (memberDTO == null) {
            throw new InvalidMemberException();
        }

        // tag O -> 해당 태그에 맞는 상품들을 1. 조회수 내림차순 2. 가격 오름차순으로 정렬
        // tag X -> 전체 상품을 1. 조회수 내림차순 2. 가격 오름차순으로 정렬
        Slice<Product> products = (tagIds != null && !tagIds.isEmpty())
            ? getProductsByTagIds(tagIds, pageable)
            : productRepository.findAllByOrderByViewCountDescPriceAsc(pageable);

        return products.map(product -> {
            ProductTagDto tagDto = productTagMappingService.getTagForProduct(product.getId());
            return ProductResponse.from(product, tagDto);
        });
    }

    private Slice<Product> getProductsByTagIds(List<Long> tagIds, Pageable pageable) {
        List<Long> productIds = productTagMappingService.getProductIdsByTagIds(tagIds);
        return productRepository.findByIdInOrderByViewCountDescPriceAsc(productIds, pageable);
    }

    // 특정 상품 선택 시 해당 상품 URL 반환
    @Transactional
    public String incrementViewCountAndGetUrl(Long productId, MemberDTO memberDTO) {
        if (memberDTO == null) {
            throw new InvalidMemberException();
        }

        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        product.incrementViewCount(); // 조회수 증가
        return product.getProductUrl(); // 상품 URL 반환
    }

}
