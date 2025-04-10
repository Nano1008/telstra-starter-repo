import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features/sim_card_activator.feature",
        glue = "stepDefinitions",
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class CucumberTest {
}
