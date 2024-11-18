package homeTry.product.service;

import homeTry.product.dto.request.ProductRequest;
import homeTry.product.dto.response.ProductAdminResponse;
import homeTry.product.exception.badRequestException.*;
import homeTry.product.model.entity.Product;
import homeTry.product.model.entity.ProductTagMapping;
import homeTry.product.repository.ProductRepository;
import homeTry.tag.productTag.dto.ProductTagDto;
import homeTry.tag.productTag.exception.badRequestException.ProductTagNotFoundException;
import homeTry.tag.productTag.model.entity.ProductTag;
import homeTry.tag.productTag.repository.ProductTagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductTagMappingService productTagMappingService;

    public AdminProductService(ProductRepository productRepository,
        ProductTagRepository productTagRepository,
        ProductTagMappingService productTagMappingService) {
        this.productRepository = productRepository;
        this.productTagRepository = productTagRepository;
        this.productTagMappingService = productTagMappingService;
    }

    // 상품 추가
    @Transactional
    public void addProduct(ProductRequest request) {

        if (request.tagId() == null) {
            throw new MissingProductTagException();
        }

        Product product = new Product(
            request.imageUrl(),
            request.productUrl(),
            request.name(),
            request.price(),
            request.storeName()
        );
        productRepository.save(product);

        ProductTag productTag = productTagRepository.findById(request.tagId())
            .orElseThrow(ProductTagNotFoundException::new);

        ProductTagMapping mapping = new ProductTagMapping(product, productTag);
        productTagMappingService.save(mapping);  // 태그 매핑 저장
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        // 연관된 ProductTagMapping soft delete
        productTagMappingService.setMappingsDeprecatedByProductId(productId);

        // 해당 Product soft delete
        product.markAsDeprecated();
        productRepository.save(product);
    }

    // 상품 전체 조회
    @Transactional(readOnly = true)
    public Page<ProductAdminResponse> getProducts(Pageable pageable) {

        return productRepository.findAllNonDeprecated(pageable)
            .map(product -> {
                ProductTagDto tagDto = productTagMappingService.getTagForProduct(product.getId());
                return ProductAdminResponse.from(product, tagDto);
            });
    }

    // 상품 단일 조회
    @Transactional(readOnly = true)
    public ProductAdminResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        ProductTagDto tagDto = productTagMappingService.getTagForProduct(product.getId());
        return ProductAdminResponse.from(product, tagDto);
    }

    // 상품 수정
    @Transactional
    public void updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        product.update(request.imageUrl(), request.productUrl(), request.name(), request.price(),
            request.storeName());

        // 태그 업데이트
        if (request.tagId() != null) {
            ProductTag newTag = productTagRepository.findById(request.tagId())
                .orElseThrow(ProductTagNotFoundException::new);

            productTagMappingService.updateProductTagMapping(product, newTag);
        }
    }

}
