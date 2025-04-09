package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.model.SimActivationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimActivationLogRepository extends JpaRepository<SimActivationLog, Long> {
}
