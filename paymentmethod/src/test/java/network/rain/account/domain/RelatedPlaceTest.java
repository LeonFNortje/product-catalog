package network.rain.account.domain;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.account.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedPlaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedPlace.class);
        RelatedPlace relatedPlace1 = new RelatedPlace();
        relatedPlace1.setId("id1");
        RelatedPlace relatedPlace2 = new RelatedPlace();
        relatedPlace2.setId(relatedPlace1.getId());
        assertThat(relatedPlace1).isEqualTo(relatedPlace2);
        relatedPlace2.setId("id2");
        assertThat(relatedPlace1).isNotEqualTo(relatedPlace2);
        relatedPlace1.setId(null);
        assertThat(relatedPlace1).isNotEqualTo(relatedPlace2);
    }
}
