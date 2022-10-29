package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourceSpecificationRefMapperTest {

    private ResourceSpecificationRefMapper resourceSpecificationRefMapper;

    @BeforeEach
    public void setUp() {
        resourceSpecificationRefMapper = new ResourceSpecificationRefMapperImpl();
    }
}
