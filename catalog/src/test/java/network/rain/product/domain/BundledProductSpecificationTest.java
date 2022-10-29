package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BundledProductSpecificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BundledProductSpecification.class);
        BundledProductSpecification bundledProductSpecification1 = new BundledProductSpecification();
        bundledProductSpecification1.setId("id1");
        BundledProductSpecification bundledProductSpecification2 = new BundledProductSpecification();
        bundledProductSpecification2.setId(bundledProductSpecification1.getId());
        assertThat(bundledProductSpecification1).isEqualTo(bundledProductSpecification2);
        bundledProductSpecification2.setId("id2");
        assertThat(bundledProductSpecification1).isNotEqualTo(bundledProductSpecification2);
        bundledProductSpecification1.setId(null);
        assertThat(bundledProductSpecification1).isNotEqualTo(bundledProductSpecification2);
    }
}
