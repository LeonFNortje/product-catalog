package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecificationCharacteristicRelationshipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecificationCharacteristicRelationshipDTO.class);
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO1 = new ProductSpecificationCharacteristicRelationshipDTO();
        productSpecificationCharacteristicRelationshipDTO1.setId("id1");
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO2 = new ProductSpecificationCharacteristicRelationshipDTO();
        assertThat(productSpecificationCharacteristicRelationshipDTO1).isNotEqualTo(productSpecificationCharacteristicRelationshipDTO2);
        productSpecificationCharacteristicRelationshipDTO2.setId(productSpecificationCharacteristicRelationshipDTO1.getId());
        assertThat(productSpecificationCharacteristicRelationshipDTO1).isEqualTo(productSpecificationCharacteristicRelationshipDTO2);
        productSpecificationCharacteristicRelationshipDTO2.setId("id2");
        assertThat(productSpecificationCharacteristicRelationshipDTO1).isNotEqualTo(productSpecificationCharacteristicRelationshipDTO2);
        productSpecificationCharacteristicRelationshipDTO1.setId(null);
        assertThat(productSpecificationCharacteristicRelationshipDTO1).isNotEqualTo(productSpecificationCharacteristicRelationshipDTO2);
    }
}
