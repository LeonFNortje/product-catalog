package network.rain.productcatalog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import network.rain.productcatalog.RainProductCatalogApp;
import network.rain.productcatalog.config.AsyncSyncConfiguration;
import network.rain.productcatalog.config.EmbeddedKafka;
import network.rain.productcatalog.config.EmbeddedSQL;
import network.rain.productcatalog.config.TestSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { RainProductCatalogApp.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedKafka
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
