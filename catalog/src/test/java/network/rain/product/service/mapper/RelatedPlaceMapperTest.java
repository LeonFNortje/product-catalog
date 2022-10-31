package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelatedPlaceMapperTest {

    private RelatedPlaceMapper relatedPlaceMapper;

    @BeforeEach
    public void setUp() {
        relatedPlaceMapper = new RelatedPlaceMapperImpl();
    }
}
