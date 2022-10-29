package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceSpecificationRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSpecificationRefDTO.class);
        ServiceSpecificationRefDTO serviceSpecificationRefDTO1 = new ServiceSpecificationRefDTO();
        serviceSpecificationRefDTO1.setId("id1");
        ServiceSpecificationRefDTO serviceSpecificationRefDTO2 = new ServiceSpecificationRefDTO();
        assertThat(serviceSpecificationRefDTO1).isNotEqualTo(serviceSpecificationRefDTO2);
        serviceSpecificationRefDTO2.setId(serviceSpecificationRefDTO1.getId());
        assertThat(serviceSpecificationRefDTO1).isEqualTo(serviceSpecificationRefDTO2);
        serviceSpecificationRefDTO2.setId("id2");
        assertThat(serviceSpecificationRefDTO1).isNotEqualTo(serviceSpecificationRefDTO2);
        serviceSpecificationRefDTO1.setId(null);
        assertThat(serviceSpecificationRefDTO1).isNotEqualTo(serviceSpecificationRefDTO2);
    }
}
