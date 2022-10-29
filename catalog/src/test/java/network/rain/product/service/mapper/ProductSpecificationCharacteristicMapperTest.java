package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductSpecificationCharacteristicMapperTest {

    private ProductSpecificationCharacteristicMapper productSpecificationCharacteristicMapper;

    @BeforeEach
    public void setUp() {
        productSpecificationCharacteristicMapper = new ProductSpecificationCharacteristicMapperImpl();
    }
}
