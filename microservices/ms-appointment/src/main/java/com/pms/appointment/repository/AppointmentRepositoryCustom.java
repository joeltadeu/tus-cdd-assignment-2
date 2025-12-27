package com.pms.appointment.repository;

import com.pms.appointment.model.Appointment;
import com.pms.models.dto.appointment.AppointmentFilter;
import org.springframework.data.domain.Page;

public interface AppointmentRepositoryCustom {
    Page<Appointment> findAllWithFilters(AppointmentFilter filter);
}
