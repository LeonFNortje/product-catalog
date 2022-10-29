package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacteristicValueSpecificationMapperTest {

    private CharacteristicValueSpecificationMapper characteristicValueSpecificationMapper;

    @BeforeEach
    public void setUp() {
        characteristicValueSpecificationMapper = new CharacteristicValueSpecificationMapperImpl();
    }
}
