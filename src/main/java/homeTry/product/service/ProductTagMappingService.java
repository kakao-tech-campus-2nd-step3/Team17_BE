package homeTry.product.service;

import homeTry.product.model.entity.ProductTagMapping;
import homeTry.product.repository.ProductTagMappingRepository;
import homeTry.tag.productTag.dto.ProductTagDto;
import homeTry.tag.productTag.exception.BadRequestException.ProductTagNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductTagMappingService {

    private final ProductTagMappingRepository productTagMappingRepository;

    public ProductTagMappingService(ProductTagMappingRepository productTagMappingRepository) {
        this.productTagMappingRepository = productTagMappingRepository;
    }

    // 태그에 맞는 상품 ID 리스트 반환
    @Transactional(readOnly = true)
    public List<Long> getProductIdsByTagIds(List<Long> tagIds) {
        List<ProductTagMapping> mappings = productTagMappingRepository.findByProductTagIdIn(tagIds);

        return mappings.stream()
            .map(mapping -> mapping.getProduct().getId())
            .toList();
    }

    // 상품 ID로 태그 정보 반환
    @Transactional(readOnly = true)
    public ProductTagDto getTagForProduct(Long productId) {
        ProductTagMapping mapping = productTagMappingRepository.findByProductId(productId)
            .orElseThrow(ProductTagNotFoundException::new);

        return ProductTagDto.from(mapping.getProductTag());
    }

}
