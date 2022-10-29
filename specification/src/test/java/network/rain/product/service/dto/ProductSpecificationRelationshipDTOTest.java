package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecificationRelationshipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecificationRelationshipDTO.class);
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO1 = new ProductSpecificationRelationshipDTO();
        productSpecificationRelationshipDTO1.setId("id1");
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO2 = new ProductSpecificationRelationshipDTO();
        assertThat(productSpecificationRelationshipDTO1).isNotEqualTo(productSpecificationRelationshipDTO2);
        productSpecificationRelationshipDTO2.setId(productSpecificationRelationshipDTO1.getId());
        assertThat(productSpecificationRelationshipDTO1).isEqualTo(productSpecificationRelationshipDTO2);
        productSpecificationRelationshipDTO2.setId("id2");
        assertThat(productSpecificationRelationshipDTO1).isNotEqualTo(productSpecificationRelationshipDTO2);
        productSpecificationRelationshipDTO1.setId(null);
        assertThat(productSpecificationRelationshipDTO1).isNotEqualTo(productSpecificationRelationshipDTO2);
    }
}
