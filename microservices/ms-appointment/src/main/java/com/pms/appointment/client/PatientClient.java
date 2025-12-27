package com.pms.appointment.client;

import com.pms.models.dto.patient.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="patient-client", url = "${client.patient.url}")
public interface PatientClient {

    @GetMapping("/v1/patients/{id}")
    PatientResponse findById(@PathVariable Long id);
}
