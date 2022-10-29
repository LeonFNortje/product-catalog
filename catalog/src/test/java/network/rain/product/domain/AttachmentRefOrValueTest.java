package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachmentRefOrValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentRefOrValue.class);
        AttachmentRefOrValue attachmentRefOrValue1 = new AttachmentRefOrValue();
        attachmentRefOrValue1.setId("id1");
        AttachmentRefOrValue attachmentRefOrValue2 = new AttachmentRefOrValue();
        attachmentRefOrValue2.setId(attachmentRefOrValue1.getId());
        assertThat(attachmentRefOrValue1).isEqualTo(attachmentRefOrValue2);
        attachmentRefOrValue2.setId("id2");
        assertThat(attachmentRefOrValue1).isNotEqualTo(attachmentRefOrValue2);
        attachmentRefOrValue1.setId(null);
        assertThat(attachmentRefOrValue1).isNotEqualTo(attachmentRefOrValue2);
    }
}
