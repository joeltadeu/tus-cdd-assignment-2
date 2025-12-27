package com.pms.appointment.client;

import com.pms.models.dto.doctor.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "doctor-client", url = "${client.doctor.url}")
public interface DoctorClient {

    @GetMapping("/v1/doctors/{id}")
    DoctorResponse findById(@PathVariable Long id);
}
