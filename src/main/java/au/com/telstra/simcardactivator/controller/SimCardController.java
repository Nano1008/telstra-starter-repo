package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.model.SimActivationLog;
import au.com.telstra.simcardactivator.repository.SimActivationLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimCardController {

    private final RestTemplate restTemplate;
    private final SimActivationLogRepository logRepository;

    public SimCardController(RestTemplate restTemplate, SimActivationLogRepository logRepository) {
        this.restTemplate = restTemplate;
        this.logRepository = logRepository;
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activateSim(@RequestBody SimActivationRequest request) {
        // Build actuator request
        ActuatorRequest actuatorRequest = new ActuatorRequest(request.getIccid());
        String actuatorUrl = "http://localhost:8444/actuate";
        ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
                actuatorUrl, actuatorRequest, ActuatorResponse.class);

        boolean activationSuccess = response.getBody() != null && response.getBody().isSuccess();

        // Save log to database
        SimActivationLog log = new SimActivationLog(request.getIccid(), request.getCustomerEmail(), activationSuccess);
        logRepository.save(log);

        // Return response
        return ResponseEntity.ok(activationSuccess);
    }

    @GetMapping("/logs")
    public ResponseEntity<SimActivationLog> getLog(@RequestParam Long id) {
        SimActivationLog log = logRepository.findById(id).orElse(null);
        if (log == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(log);
    }
}
