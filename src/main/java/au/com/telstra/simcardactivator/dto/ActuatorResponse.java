package au.com.telstra.simcardactivator.dto;

public record ActuatorResponse(boolean success) {
    public boolean isSuccess() {
        return success;
    }
}
