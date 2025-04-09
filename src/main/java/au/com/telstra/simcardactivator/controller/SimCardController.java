package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import au.com.telstra.simcardactivator.dto.ActuatorRequest;
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
        ActuatorRequest actuatorRequest = new ActuatorRequest(request.iccid());

        // Send the POST request to the actuator microservice
        String actuatorUrl = "http://localhost:8444/actuate";

        return restTemplate.postForEntity(actuatorUrl, actuatorRequest, String.class);

    }
}
