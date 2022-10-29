package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceSpecificationRefMapperTest {

    private ServiceSpecificationRefMapper serviceSpecificationRefMapper;

    @BeforeEach
    public void setUp() {
        serviceSpecificationRefMapper = new ServiceSpecificationRefMapperImpl();
    }
}
