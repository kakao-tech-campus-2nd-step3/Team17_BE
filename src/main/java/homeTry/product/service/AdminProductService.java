package homeTry.product.service;

import homeTry.product.dto.request.ProductRequest;
import homeTry.product.dto.response.ProductResponse;
import homeTry.product.exception.badRequestException.ProductNotFoundException;
import homeTry.product.model.entity.Product;
import homeTry.product.repository.ProductRepository;
import homeTry.tag.productTag.dto.ProductTagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;
    private final ProductTagMappingService productTagMappingService;

    public AdminProductService(ProductRepository productRepository,
        ProductTagMappingService productTagMappingService) {
        this.productRepository = productRepository;
        this.productTagMappingService = productTagMappingService;
    }

    // 상품 추가
    public void createProduct(ProductRequest request) {
        Product product = new Product(
            request.imageUrl(),
            request.productUrl(),
            request.name(),
            request.price(),
            request.storeName()
        );
        productRepository.save(product);
    }

    // 상품 삭제
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(productId);
    }

    // 상품 조회
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAllByOrderByIdAsc(pageable)
            .map(product -> {
                ProductTagDto tagDto = productTagMappingService.getTagForProduct(product.getId());
                return ProductResponse.from(product, tagDto);
            });
    }


}
