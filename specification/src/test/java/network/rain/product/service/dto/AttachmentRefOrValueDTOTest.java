package network.rain.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachmentRefOrValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentRefOrValueDTO.class);
        AttachmentRefOrValueDTO attachmentRefOrValueDTO1 = new AttachmentRefOrValueDTO();
        attachmentRefOrValueDTO1.setId("id1");
        AttachmentRefOrValueDTO attachmentRefOrValueDTO2 = new AttachmentRefOrValueDTO();
        assertThat(attachmentRefOrValueDTO1).isNotEqualTo(attachmentRefOrValueDTO2);
        attachmentRefOrValueDTO2.setId(attachmentRefOrValueDTO1.getId());
        assertThat(attachmentRefOrValueDTO1).isEqualTo(attachmentRefOrValueDTO2);
        attachmentRefOrValueDTO2.setId("id2");
        assertThat(attachmentRefOrValueDTO1).isNotEqualTo(attachmentRefOrValueDTO2);
        attachmentRefOrValueDTO1.setId(null);
        assertThat(attachmentRefOrValueDTO1).isNotEqualTo(attachmentRefOrValueDTO2);
    }
}
