package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TargetProductSchemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetProductSchema.class);
        TargetProductSchema targetProductSchema1 = new TargetProductSchema();
        targetProductSchema1.setId(1L);
        TargetProductSchema targetProductSchema2 = new TargetProductSchema();
        targetProductSchema2.setId(targetProductSchema1.getId());
        assertThat(targetProductSchema1).isEqualTo(targetProductSchema2);
        targetProductSchema2.setId(2L);
        assertThat(targetProductSchema1).isNotEqualTo(targetProductSchema2);
        targetProductSchema1.setId(null);
        assertThat(targetProductSchema1).isNotEqualTo(targetProductSchema2);
    }
}
