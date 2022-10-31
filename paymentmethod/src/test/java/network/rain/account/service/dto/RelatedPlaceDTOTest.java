package network.rain.account.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import network.rain.account.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedPlaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedPlaceDTO.class);
        RelatedPlaceDTO relatedPlaceDTO1 = new RelatedPlaceDTO();
        relatedPlaceDTO1.setId("id1");
        RelatedPlaceDTO relatedPlaceDTO2 = new RelatedPlaceDTO();
        assertThat(relatedPlaceDTO1).isNotEqualTo(relatedPlaceDTO2);
        relatedPlaceDTO2.setId(relatedPlaceDTO1.getId());
        assertThat(relatedPlaceDTO1).isEqualTo(relatedPlaceDTO2);
        relatedPlaceDTO2.setId("id2");
        assertThat(relatedPlaceDTO1).isNotEqualTo(relatedPlaceDTO2);
        relatedPlaceDTO1.setId(null);
        assertThat(relatedPlaceDTO1).isNotEqualTo(relatedPlaceDTO2);
    }
}
