package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TargetProductSchemaMapperTest {

    private TargetProductSchemaMapper targetProductSchemaMapper;

    @BeforeEach
    public void setUp() {
        targetProductSchemaMapper = new TargetProductSchemaMapperImpl();
    }
}