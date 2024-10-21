package homeTry.product.model.entity;

import homeTry.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// TODO : 원시값 포장하기
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String productUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String storeName;

    protected Product() {

    }

    public Product(String imageUrl, String productUrl, String name, Long price, String storeName) {
        this.imageUrl = imageUrl;
        this.productUrl = productUrl;
        this.name = name;
        this.price = price;
        this.storeName = storeName;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getStoreName() {
        return storeName;
    }
}
