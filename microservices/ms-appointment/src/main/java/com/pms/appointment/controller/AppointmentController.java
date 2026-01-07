package com.pms.appointment.controller;

import static com.pms.appointment.controller.constants.AppointmentConstants.*;

import com.pms.appointment.controller.mapper.AppointmentMapper;
import com.pms.appointment.service.AppointmentService;
import com.pms.controller.PmsController;
import com.pms.models.dto.appointment.AppointmentResponse;
import com.pms.models.dto.appointment.CreateAppointmentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/appointments")
@AllArgsConstructor
@Slf4j
public class AppointmentController implements PmsController {
    private final AppointmentService service;
    private final AppointmentMapper mapper;

    @Operation(summary = "Register an Appointment",
            description = "This endpoint is responsible to register a new appointment",
            security = @SecurityRequirement(name = AUTHORIZATION))
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_CREATED, description = "Appointment created",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AppointmentResponse.class))
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_BAD_REQUEST, description = "Appointment request is invalid",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_BAD_REQUEST_NAME,
                                            description = "A bad request response example when trying to register an appointment",
                                            value = APPOINTMENT_EXAMPLE_ERROR_400_BAD_REQUEST)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during register the appointment",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to register an appointment",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @PostMapping
    public ResponseEntity<AppointmentResponse> insert(
            @RequestBody
            @Valid @NotNull CreateAppointmentRequest request) {
        log.info("Request for create an appointment. appointment:{}", request);
        var appointment = mapper.toAppointment(request);
        var savedAppointment = service.insert(appointment);

        return ResponseEntity.created(getURI(savedAppointment.getId())).body(savedAppointment);
    }

    @Operation(summary = "Retrieve an appointment by id",
            description = "This endpoint is responsible to retrieve the appointment data by id",
            security = @SecurityRequirement(name = AUTHORIZATION),
            parameters = {@Parameter(name = "id", description = "Id of the appointment to be searched", example = "1", in = ParameterIn.PATH)})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_OK, description = "Return appointment",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AppointmentResponse.class))
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_NOT_FOUND, description = "Appointment not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_NOT_FOUND_NAME,
                                            description = "A not found response example when trying to retrieve an appointment does not exist",
                                            value = APPOINTMENT_EXAMPLE_ERROR_404_NOT_FOUND)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during retrieve the doctor",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to retrieve a doctor",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findById(
            @PathVariable
            Long id) {
        log.info("Request for find appointment by id [{}]", id);

        final var appointment = service.findByIdEnriched(id);
        return ResponseEntity.ok(appointment);
    }

    @Operation(summary = "Delete the appointment by id",
            description = "This endpoint is responsible to delete the appointment by id",
            security = @SecurityRequirement(name = AUTHORIZATION),
            parameters = {@Parameter(name = "id", description = "Id of the appointment to be deleted", example = "1", in = ParameterIn.PATH)})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_OK, description = "Appointment deleted",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_NOT_FOUND,
                    description = "Appointment not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_NOT_FOUND_NAME,
                                            description = "A not found response example when trying to delete an appointment does not exist",
                                            value = APPOINTMENT_EXAMPLE_ERROR_404_NOT_FOUND)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during delete the appointment",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to delete an appointment",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            Long id) {
        log.info("Request for delete appointment by id [{}]", id);

        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
