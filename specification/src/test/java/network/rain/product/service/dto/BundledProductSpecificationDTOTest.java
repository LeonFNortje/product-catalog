package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BundledProductSpecificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BundledProductSpecificationDTO.class);
        BundledProductSpecificationDTO bundledProductSpecificationDTO1 = new BundledProductSpecificationDTO();
        bundledProductSpecificationDTO1.setId("id1");
        BundledProductSpecificationDTO bundledProductSpecificationDTO2 = new BundledProductSpecificationDTO();
        assertThat(bundledProductSpecificationDTO1).isNotEqualTo(bundledProductSpecificationDTO2);
        bundledProductSpecificationDTO2.setId(bundledProductSpecificationDTO1.getId());
        assertThat(bundledProductSpecificationDTO1).isEqualTo(bundledProductSpecificationDTO2);
        bundledProductSpecificationDTO2.setId("id2");
        assertThat(bundledProductSpecificationDTO1).isNotEqualTo(bundledProductSpecificationDTO2);
        bundledProductSpecificationDTO1.setId(null);
        assertThat(bundledProductSpecificationDTO1).isNotEqualTo(bundledProductSpecificationDTO2);
    }
}
