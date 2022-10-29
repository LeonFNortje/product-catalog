package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceSpecificationRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSpecificationRef.class);
        ServiceSpecificationRef serviceSpecificationRef1 = new ServiceSpecificationRef();
        serviceSpecificationRef1.setId("id1");
        ServiceSpecificationRef serviceSpecificationRef2 = new ServiceSpecificationRef();
        serviceSpecificationRef2.setId(serviceSpecificationRef1.getId());
        assertThat(serviceSpecificationRef1).isEqualTo(serviceSpecificationRef2);
        serviceSpecificationRef2.setId("id2");
        assertThat(serviceSpecificationRef1).isNotEqualTo(serviceSpecificationRef2);
        serviceSpecificationRef1.setId(null);
        assertThat(serviceSpecificationRef1).isNotEqualTo(serviceSpecificationRef2);
    }
}
