Feature: Patient - Create, Update, Get by Id, Get All, Delete
  Background:
    * def patient_url = patientHost + config.uri.patient
    * def RandomData = Java.type('com.pms.integration.util.RandomData')

  @PatientService
  @CreatingPatient
  Scenario: Creating a Patient
    * def patientResponse = call read('Patient.feature@CreatePatient')
    And match patientResponse.response.id == '#notnull'
    * call read('Patient.feature@DeletePatient') { id: '#(patientResponse.response.id)'}


  @PatientService
  @UpdatingPatient
  Scenario: Updating a Patient
    * def patientCreateResponse = call read('Patient.feature@CreatePatient')
    And match patientCreateResponse.response.id == '#notnull'
    * def patientId = patientCreateResponse.response.id
    * call read('Patient.feature@UpdatePatient') { id: '#(patientId)', firstName: 'Karate', lastName: 'Patient', email: 'karate.patient@gmail.com'}
    * def patientGetResponse = call read('Patient.feature@GetPatientById') { id: '#(patientId)' }
    And match patientGetResponse.response.firstName == 'Karate'
    And match patientGetResponse.response.lastName == 'Patient'
    And match patientGetResponse.response.email == 'karate.patient@gmail.com'
    * call read('Patient.feature@DeletePatient') { id: '#(patientId)'}

  @PatientService
  @GettingPatient
  Scenario: Getting a Patient
    * def patientCreateResponse = call read('Patient.feature@CreatePatient')
    And match patientCreateResponse.response.id == '#notnull'
    * def patientGetResponse = call read('Patient.feature@GetPatientById') { id: '#(patientCreateResponse.response.id)' }
    * call read('Patient.feature@DeletePatient') { id: '#(patientGetResponse.response.id)'}

  @PatientService
  @DeletingPatient
  Scenario: Deleting a Patient
    * def patientCreateResponse = call read('Patient.feature@CreatePatient')
    And match patientCreateResponse.response.id == '#notnull'
    * print patientCreateResponse
    * def patientId = patientCreateResponse.response.id
    * call read('Patient.feature@DeletePatient') { id: '#(patientId)'}
    * def patientGetResponse = call read('Patient.feature@GetPatientByIdNotFound') { id: '#(patientId)' }

  @PatientService
  @GetPatientList
  Scenario: Get Patient List
    * def patientCreateResponse = call read('Patient.feature@CreatePatient')
    And match patientCreateResponse.response.id == '#notnull'
    Given url patient_url
    When method GET
    Then status 200
    And match each response.content[*] == '#notnull'
    And match each response.content[*] contains deep {id: '#number'}
    And match each response.content[*] contains deep {firstName: '#string'}
    And match each response.content[*] contains deep {lastName: '#string'}
    And match each response.content[*] contains deep {email: '#string'}
    And match each response.content[*] contains deep {address: '#string'}
    And match each response.content[*] contains deep {dateOfBirth: '#string'}
    * call read('Patient.feature@DeletePatient') { id: '#(patientCreateResponse.response.id)'}

  @CreatePatient
  @Ignore
  Scenario: Insert Patient
    * def req = read('data/patient_request.json')
    * req.firstName = RandomData.randomFirstName()
    * req.lastName = RandomData.randomLastName()
    * req.email = RandomData.randomEmail(req.firstName, req.lastName)
    Given url patient_url
    And request req
    When method POST
    Then status 201

  @CreatePatientWithParams
  @Ignore
  Scenario: Insert Patient
    * def req = read('data/patient_request.json')
    * req.firstName = firstName
    * req.lastName = lastName
    * req.email = email
    Given url patient_url
    And request req
    When method POST
    Then status 201

  @GetPatientByIdNotFound
  @Ignore
  Scenario: Get Patient By Id
    Given url patient_url + '/' + id
    When method GET
    Then status 404
    And match response == '#notnull'
    And match response contains deep {code: '#number'}
    And match response contains deep {status: '#string'}
    And match response contains deep {description: '#string'}

  @GetPatientById
  @Ignore
  Scenario: Get Patient By Id
    Given url patient_url + '/' + id
    When method GET
    Then status 200
    And match response == '#notnull'
    * print response
    And match response contains deep {id: '#number'}
    And match response contains deep {firstName: '#string'}
    And match response contains deep {lastName: '#string'}
    And match response contains deep {email: '#string'}
    And match response contains deep {address: '#string'}
    And match response contains deep {dateOfBirth: '#string'}


  @UpdatePatient
  @Ignore
  Scenario: Update Patient
    * def req = read('data/patient_request.json')
    * req.firstName = firstName
    * req.lastName = lastName
    * req.email = email
    Given url patient_url + '/' + id
    And request req
    When method PUT
    Then status 200

  @DeletePatient
  @Ignore
  Scenario: Delete Patient
    Given url patient_url + '/' + id
    When method DELETE
    Then status 204