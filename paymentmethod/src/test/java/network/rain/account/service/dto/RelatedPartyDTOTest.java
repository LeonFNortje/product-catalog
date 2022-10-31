package network.rain.account.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.account.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedPartyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedPartyDTO.class);
        RelatedPartyDTO relatedPartyDTO1 = new RelatedPartyDTO();
        relatedPartyDTO1.setId("id1");
        RelatedPartyDTO relatedPartyDTO2 = new RelatedPartyDTO();
        assertThat(relatedPartyDTO1).isNotEqualTo(relatedPartyDTO2);
        relatedPartyDTO2.setId(relatedPartyDTO1.getId());
        assertThat(relatedPartyDTO1).isEqualTo(relatedPartyDTO2);
        relatedPartyDTO2.setId("id2");
        assertThat(relatedPartyDTO1).isNotEqualTo(relatedPartyDTO2);
        relatedPartyDTO1.setId(null);
        assertThat(relatedPartyDTO1).isNotEqualTo(relatedPartyDTO2);
    }
}
