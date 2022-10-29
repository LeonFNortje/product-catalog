package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CharacteristicValueSpecificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CharacteristicValueSpecification.class);
        CharacteristicValueSpecification characteristicValueSpecification1 = new CharacteristicValueSpecification();
        characteristicValueSpecification1.setId(1L);
        CharacteristicValueSpecification characteristicValueSpecification2 = new CharacteristicValueSpecification();
        characteristicValueSpecification2.setId(characteristicValueSpecification1.getId());
        assertThat(characteristicValueSpecification1).isEqualTo(characteristicValueSpecification2);
        characteristicValueSpecification2.setId(2L);
        assertThat(characteristicValueSpecification1).isNotEqualTo(characteristicValueSpecification2);
        characteristicValueSpecification1.setId(null);
        assertThat(characteristicValueSpecification1).isNotEqualTo(characteristicValueSpecification2);
    }
}
