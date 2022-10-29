package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResourceSpecificationRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceSpecificationRefDTO.class);
        ResourceSpecificationRefDTO resourceSpecificationRefDTO1 = new ResourceSpecificationRefDTO();
        resourceSpecificationRefDTO1.setId("id1");
        ResourceSpecificationRefDTO resourceSpecificationRefDTO2 = new ResourceSpecificationRefDTO();
        assertThat(resourceSpecificationRefDTO1).isNotEqualTo(resourceSpecificationRefDTO2);
        resourceSpecificationRefDTO2.setId(resourceSpecificationRefDTO1.getId());
        assertThat(resourceSpecificationRefDTO1).isEqualTo(resourceSpecificationRefDTO2);
        resourceSpecificationRefDTO2.setId("id2");
        assertThat(resourceSpecificationRefDTO1).isNotEqualTo(resourceSpecificationRefDTO2);
        resourceSpecificationRefDTO1.setId(null);
        assertThat(resourceSpecificationRefDTO1).isNotEqualTo(resourceSpecificationRefDTO2);
    }
}
