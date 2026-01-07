Feature: Appointment - Create, Get by Id
  Background:
    * def appointment_url = appointmentHost + config.uri.appointment

  @AppointmentService
  @CreatingAppointment
  Scenario: Creating a Appointment
    * def doctorResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorResponse.response.id == '#notnull'
    * def doctorId = doctorResponse.response.id
    * def patientResponse = call read('Patient.feature@CreatePatient')
    And match patientResponse.response.id == '#notnull'
    * def patientId = patientResponse.response.id
    * def appointmentResponse = call read('Appointment.feature@CreateAppointment') { doctorId: '#(doctorId)', patientId: '#(patientId)'}
    * def appointmentId = appointmentResponse.response.id
    * call read('Appointment.feature@DeleteAppointment') { id: '#(appointmentId)'}
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorId)'}
    * call read('Patient.feature@DeletePatient') { id: '#(patientId)'}


  @AppointmentService
  @GettingAppointment
  Scenario: Getting a Appointment
    * def doctorResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorResponse.response.id == '#notnull'
    * def doctorId = doctorResponse.response.id
    * def patientResponse = call read('Patient.feature@CreatePatient')
    And match patientResponse.response.id == '#notnull'
    * def patientId = patientResponse.response.id
    * def appointmentResponse = call read('Appointment.feature@CreateAppointment') { doctorId: '#(doctorId)', patientId: '#(patientId)'}
    * def appointmentId = appointmentResponse.response.id
    * def appointmentGetResponse = call read('Appointment.feature@GetAppointmentById') { id: '#(appointmentId)' }
    * call read('Appointment.feature@DeleteAppointment') { id: '#(appointmentId)'}
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorId)'}
    * call read('Patient.feature@DeletePatient') { id: '#(patientId)'}


  @CreateAppointment
  @Ignore
  Scenario: Insert Appointment
    * def req = read('data/appointment_request.json')
    * def now = new Date()
    * def formatter = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    * req.startTime = formatter.format(now)
    * req.patientId = patientId
    * req.doctorId = doctorId
    Given url appointment_url
    And request req
    When method POST
    Then status 201

  @GetAppointmentByIdNotFound
  @Ignore
  Scenario: Get Appointment By Id
    Given url appointment_url + '/' + id
    When method GET
    Then status 404
    And match response == '#notnull'
    And match response == '#notnull'
    And match response contains deep {code: '#number'}
    And match response contains deep {status: '#string'}
    And match response contains deep {description: '#string'}

  @GetAppointmentById
  @Ignore
  Scenario: Get Appointment By Id
    Given url appointment_url + '/' + id
    When method GET
    Then status 200
    And match response == '#notnull'
    And match response contains deep {patient: {id: '#number', firstName: '#string', lastName: '#string', email: '#string'}}
    And match response contains deep {doctor: {id: '#number', firstName: '#string', lastName: '#string', title: '#string', speciality: '#string'}}
    And match response contains deep {id: '#number'}
    And match response contains deep {startTime: '#string'}
    And match response contains deep {endTime: '#string'}
    And match response contains deep {duration: '#number'}
    And match response contains deep {description: '#string'}
    And match response contains deep {type: '#string'}
    And match response contains deep {status: '#string'}


  @DeleteAppointment
  @Ignore
  Scenario: Delete Appointment
    Given url appointment_url + '/' + id
    When method DELETE
    Then status 204