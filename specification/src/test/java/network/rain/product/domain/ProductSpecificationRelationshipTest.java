package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecificationRelationshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecificationRelationship.class);
        ProductSpecificationRelationship productSpecificationRelationship1 = new ProductSpecificationRelationship();
        productSpecificationRelationship1.setId("id1");
        ProductSpecificationRelationship productSpecificationRelationship2 = new ProductSpecificationRelationship();
        productSpecificationRelationship2.setId(productSpecificationRelationship1.getId());
        assertThat(productSpecificationRelationship1).isEqualTo(productSpecificationRelationship2);
        productSpecificationRelationship2.setId("id2");
        assertThat(productSpecificationRelationship1).isNotEqualTo(productSpecificationRelationship2);
        productSpecificationRelationship1.setId(null);
        assertThat(productSpecificationRelationship1).isNotEqualTo(productSpecificationRelationship2);
    }
}
