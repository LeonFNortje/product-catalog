package network.rain.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedPartyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedParty.class);
        RelatedParty relatedParty1 = new RelatedParty();
        relatedParty1.setId("id1");
        RelatedParty relatedParty2 = new RelatedParty();
        relatedParty2.setId(relatedParty1.getId());
        assertThat(relatedParty1).isEqualTo(relatedParty2);
        relatedParty2.setId("id2");
        assertThat(relatedParty1).isNotEqualTo(relatedParty2);
        relatedParty1.setId(null);
        assertThat(relatedParty1).isNotEqualTo(relatedParty2);
    }
}
