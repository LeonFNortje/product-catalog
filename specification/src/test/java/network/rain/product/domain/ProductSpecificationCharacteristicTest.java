package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecificationCharacteristicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecificationCharacteristic.class);
        ProductSpecificationCharacteristic productSpecificationCharacteristic1 = new ProductSpecificationCharacteristic();
        productSpecificationCharacteristic1.setId("id1");
        ProductSpecificationCharacteristic productSpecificationCharacteristic2 = new ProductSpecificationCharacteristic();
        productSpecificationCharacteristic2.setId(productSpecificationCharacteristic1.getId());
        assertThat(productSpecificationCharacteristic1).isEqualTo(productSpecificationCharacteristic2);
        productSpecificationCharacteristic2.setId("id2");
        assertThat(productSpecificationCharacteristic1).isNotEqualTo(productSpecificationCharacteristic2);
        productSpecificationCharacteristic1.setId(null);
        assertThat(productSpecificationCharacteristic1).isNotEqualTo(productSpecificationCharacteristic2);
    }
}
