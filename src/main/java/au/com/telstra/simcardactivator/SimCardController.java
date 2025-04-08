package au.com.telstra.simcardactivator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimCardController {

    private final RestTemplate restTemplate;

    public SimCardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        // Prepare the payload for the actuator service
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> actuatorRequest = new HttpEntity<>(String.format("{\"iccid\":\"%s\"}", request.getIccid()), headers);

        // Send the POST request to the actuator microservice
        String actuatorUrl = "http://localhost:8444/actuate";

        return restTemplate.postForEntity(actuatorUrl, actuatorRequest, String.class);

    }
}
