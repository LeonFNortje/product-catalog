package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResourceSpecificationRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceSpecificationRef.class);
        ResourceSpecificationRef resourceSpecificationRef1 = new ResourceSpecificationRef();
        resourceSpecificationRef1.setId("id1");
        ResourceSpecificationRef resourceSpecificationRef2 = new ResourceSpecificationRef();
        resourceSpecificationRef2.setId(resourceSpecificationRef1.getId());
        assertThat(resourceSpecificationRef1).isEqualTo(resourceSpecificationRef2);
        resourceSpecificationRef2.setId("id2");
        assertThat(resourceSpecificationRef1).isNotEqualTo(resourceSpecificationRef2);
        resourceSpecificationRef1.setId(null);
        assertThat(resourceSpecificationRef1).isNotEqualTo(resourceSpecificationRef2);
    }
}
