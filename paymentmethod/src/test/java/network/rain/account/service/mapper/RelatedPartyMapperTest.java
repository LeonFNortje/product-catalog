package network.rain.account.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelatedPartyMapperTest {

    private RelatedPartyMapper relatedPartyMapper;

    @BeforeEach
    public void setUp() {
        relatedPartyMapper = new RelatedPartyMapperImpl();
    }
}
