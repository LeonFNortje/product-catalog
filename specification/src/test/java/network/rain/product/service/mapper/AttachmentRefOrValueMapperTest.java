package network.rain.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttachmentRefOrValueMapperTest {

    private AttachmentRefOrValueMapper attachmentRefOrValueMapper;

    @BeforeEach
    public void setUp() {
        attachmentRefOrValueMapper = new AttachmentRefOrValueMapperImpl();
    }
}
