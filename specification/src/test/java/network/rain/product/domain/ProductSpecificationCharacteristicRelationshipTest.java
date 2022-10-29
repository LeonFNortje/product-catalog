package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecificationCharacteristicRelationshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecificationCharacteristicRelationship.class);
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship1 = new ProductSpecificationCharacteristicRelationship();
        productSpecificationCharacteristicRelationship1.setId("id1");
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship2 = new ProductSpecificationCharacteristicRelationship();
        productSpecificationCharacteristicRelationship2.setId(productSpecificationCharacteristicRelationship1.getId());
        assertThat(productSpecificationCharacteristicRelationship1).isEqualTo(productSpecificationCharacteristicRelationship2);
        productSpecificationCharacteristicRelationship2.setId("id2");
        assertThat(productSpecificationCharacteristicRelationship1).isNotEqualTo(productSpecificationCharacteristicRelationship2);
        productSpecificationCharacteristicRelationship1.setId(null);
        assertThat(productSpecificationCharacteristicRelationship1).isNotEqualTo(productSpecificationCharacteristicRelationship2);
    }
}
