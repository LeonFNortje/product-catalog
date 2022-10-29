package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TargetProductSchemaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetProductSchemaDTO.class);
        TargetProductSchemaDTO targetProductSchemaDTO1 = new TargetProductSchemaDTO();
        targetProductSchemaDTO1.setId(1L);
        TargetProductSchemaDTO targetProductSchemaDTO2 = new TargetProductSchemaDTO();
        assertThat(targetProductSchemaDTO1).isNotEqualTo(targetProductSchemaDTO2);
        targetProductSchemaDTO2.setId(targetProductSchemaDTO1.getId());
        assertThat(targetProductSchemaDTO1).isEqualTo(targetProductSchemaDTO2);
        targetProductSchemaDTO2.setId(2L);
        assertThat(targetProductSchemaDTO1).isNotEqualTo(targetProductSchemaDTO2);
        targetProductSchemaDTO1.setId(null);
        assertThat(targetProductSchemaDTO1).isNotEqualTo(targetProductSchemaDTO2);
    }
}
