package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductSpecificationCharacteristicRelationshipMapperTest {

    private ProductSpecificationCharacteristicRelationshipMapper productSpecificationCharacteristicRelationshipMapper;

    @BeforeEach
    public void setUp() {
        productSpecificationCharacteristicRelationshipMapper = new ProductSpecificationCharacteristicRelationshipMapperImpl();
    }
}
