package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    private static final String BASE_URL = "http://localhost:8080/activate";
    private static final String STATUS_URL = "http://localhost:8080/logs";

    @Autowired
    private TestRestTemplate restTemplate;

    private String iccid;
    private String email;
    private ResponseEntity<String> activationResponse;
    private Long simCardId;

    @Given("a SIM card with ICCID {string} and email {string}")
    public void a_sim_card_with_iccid_and_email(String iccid, String email) {
        this.iccid = iccid;
        this.email = email;
    }

    @When("I activate the SIM card")
    public void i_activate_the_sim_card() {
        SimActivationRequest request = new SimActivationRequest(iccid, email);
        activationResponse = restTemplate.postForEntity(BASE_URL, request, String.class);
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assertTrue(activationResponse.getBody().contains("true"));
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        assertTrue(activationResponse.getBody().contains("false"));
    }

    @And("the activation status for SIM card ID {long} should be {string}")
    public void the_activation_status_for_sim_card_id_should_be(Long id, String status) {
        simCardId = id;

        ResponseEntity<String> statusResponse = restTemplate.getForEntity(STATUS_URL + "?id=" + simCardId, String.class);
        assertTrue(statusResponse.getBody().contains(status));
    }




}