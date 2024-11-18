package homeTry.tag.productTag.service;

import java.util.List;

import homeTry.tag.model.vo.TagName;
import homeTry.tag.productTag.dto.ProductTagDto;
import homeTry.tag.productTag.dto.request.ProductTagRequest;
import homeTry.tag.productTag.dto.response.ProductTagResponse;
import homeTry.tag.productTag.exception.badRequestException.ProductTagAlreadyExistsException;
import homeTry.tag.productTag.exception.badRequestException.ProductTagNotFoundException;
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

    @Transactional(readOnly = true)
    public List<ProductTagDto> getProductTagList() {

        List<ProductTag> productTags = productTagRepository.findAllByIsDeprecatedFalse();

        return productTags
                .stream()
                .map(ProductTagDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductTagResponse getProductTagResponse() {

        List<ProductTagDto> productTagList = productTagRepository.findAllByIsDeprecatedFalse()
                .stream()
                .map(ProductTagDto::from)
                .toList();

        return new ProductTagResponse(productTagList);
    }

    @Transactional
    public void addProductTag(ProductTagRequest productTagRequest) {


        if(productTagRepository.existsByTagNameAndIsDeprecatedFalse(new TagName(productTagRequest.productTagName()))){
            throw new ProductTagAlreadyExistsException();
        }

        productTagRepository.save(
                new ProductTag(
                        productTagRequest.productTagName())
        );
    }

    @Transactional
    public void deleteProductTag(Long productTagId) {


        ProductTag productTag = productTagRepository.findById(productTagId)
                .orElseThrow(() -> new ProductTagNotFoundException());

        productTag.markAsDeprecated();
    }
}
