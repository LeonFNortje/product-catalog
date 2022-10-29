package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BundledProductSpecificationMapperTest {

    private BundledProductSpecificationMapper bundledProductSpecificationMapper;

    @BeforeEach
    public void setUp() {
        bundledProductSpecificationMapper = new BundledProductSpecificationMapperImpl();
    }
}
