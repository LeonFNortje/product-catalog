package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductSpecificationRelationshipMapperTest {

    private ProductSpecificationRelationshipMapper productSpecificationRelationshipMapper;

    @BeforeEach
    public void setUp() {
        productSpecificationRelationshipMapper = new ProductSpecificationRelationshipMapperImpl();
    }
}
