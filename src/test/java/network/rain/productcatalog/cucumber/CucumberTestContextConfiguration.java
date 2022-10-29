package network.rain.productcatalog.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import network.rain.productcatalog.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
