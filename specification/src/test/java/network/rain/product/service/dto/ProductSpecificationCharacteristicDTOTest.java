package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecificationCharacteristicDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecificationCharacteristicDTO.class);
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO1 = new ProductSpecificationCharacteristicDTO();
        productSpecificationCharacteristicDTO1.setId("id1");
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO2 = new ProductSpecificationCharacteristicDTO();
        assertThat(productSpecificationCharacteristicDTO1).isNotEqualTo(productSpecificationCharacteristicDTO2);
        productSpecificationCharacteristicDTO2.setId(productSpecificationCharacteristicDTO1.getId());
        assertThat(productSpecificationCharacteristicDTO1).isEqualTo(productSpecificationCharacteristicDTO2);
        productSpecificationCharacteristicDTO2.setId("id2");
        assertThat(productSpecificationCharacteristicDTO1).isNotEqualTo(productSpecificationCharacteristicDTO2);
        productSpecificationCharacteristicDTO1.setId(null);
        assertThat(productSpecificationCharacteristicDTO1).isNotEqualTo(productSpecificationCharacteristicDTO2);
    }
}
