package homeTry.tag.productTag.service;

import homeTry.product.model.entity.ProductTagMapping;
import homeTry.product.repository.ProductTagMappingRepository;
import java.util.List;

import homeTry.tag.productTag.dto.ProductTagDto;
import homeTry.tag.productTag.dto.request.ProductTagRequest;
import homeTry.tag.productTag.dto.response.ProductTagResponse;
import homeTry.tag.productTag.exception.BadRequestException.ProductTagNotFoundException;
import homeTry.tag.productTag.model.entity.ProductTag;
import homeTry.tag.productTag.repository.ProductTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductTagService {

    private final ProductTagRepository productTagRepository;

    public ProductTagService(ProductTagRepository productTagRepository) {
        this.productTagRepository = productTagRepository;
    }

    public ProductTagResponse getProductTagList() {

        List<ProductTagDto> productTagList = productTagRepository.findAll()
                .stream()
                .map(ProductTagDto::from)
                .toList();

        return new ProductTagResponse(productTagList);
    }

    @Transactional
    public void addProductTag(ProductTagRequest productTagRequest) {

        productTagRepository.save(
                new ProductTag(
                        productTagRequest.productTagName())
        );
    }

    @Transactional
    public void deleteProductTag(Long productTagId) {

        ProductTag productTag = productTagRepository.findById(productTagId)
                .orElseThrow(() -> new ProductTagNotFoundException());

        productTagRepository.delete(productTag);
    }
}
