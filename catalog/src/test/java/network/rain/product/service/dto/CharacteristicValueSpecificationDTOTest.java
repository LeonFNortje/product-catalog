package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CharacteristicValueSpecificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CharacteristicValueSpecificationDTO.class);
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO1 = new CharacteristicValueSpecificationDTO();
        characteristicValueSpecificationDTO1.setId(1L);
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO2 = new CharacteristicValueSpecificationDTO();
        assertThat(characteristicValueSpecificationDTO1).isNotEqualTo(characteristicValueSpecificationDTO2);
        characteristicValueSpecificationDTO2.setId(characteristicValueSpecificationDTO1.getId());
        assertThat(characteristicValueSpecificationDTO1).isEqualTo(characteristicValueSpecificationDTO2);
        characteristicValueSpecificationDTO2.setId(2L);
        assertThat(characteristicValueSpecificationDTO1).isNotEqualTo(characteristicValueSpecificationDTO2);
        characteristicValueSpecificationDTO1.setId(null);
        assertThat(characteristicValueSpecificationDTO1).isNotEqualTo(characteristicValueSpecificationDTO2);
    }
}
