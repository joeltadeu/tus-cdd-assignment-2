Feature: Doctor - Create, Update, Get by Id, Get All, Delete
  Background:
    * def doctor_url = doctorHost + config.uri.doctor
    * def RandomData = Java.type('com.pms.integration.util.RandomData')

  @DoctorService
  @CreatingDoctor
  Scenario: Creating a Doctor
    * def doctorResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorResponse.response.id == '#notnull'
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorResponse.response.id)'}


  @DoctorService
  @UpdatingDoctor
  Scenario: Updating a Doctor
    * def doctorCreateResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorCreateResponse.response.id == '#notnull'
    * def doctorId = doctorCreateResponse.response.id
    * call read('Doctor.feature@UpdateDoctor') { id: '#(doctorId)', firstName: 'Karate', lastName: 'Doctor', email: 'karate.doctor@medicalclinic.com'}
    * def doctorGetResponse = call read('Doctor.feature@GetDoctorById') { id: '#(doctorId)' }
    And match doctorGetResponse.response.firstName == 'Karate'
    And match doctorGetResponse.response.lastName == 'Doctor'
    And match doctorGetResponse.response.email == 'karate.doctor@medicalclinic.com'
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorId)'}

  @DoctorService
  @GettingDoctor
  Scenario: Getting a Doctor
    * def doctorCreateResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorCreateResponse.response.id == '#notnull'
    * def doctorGetResponse = call read('Doctor.feature@GetDoctorById') { id: '#(doctorCreateResponse.response.id)' }
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorGetResponse.response.id)'}

  @DoctorService
  @DeletingDoctor
  Scenario: Deleting a Doctor
    * def doctorCreateResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorCreateResponse.response.id == '#notnull'
    * print doctorCreateResponse
    * def doctorId = doctorCreateResponse.response.id
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorId)'}
    * def doctorGetResponse = call read('Doctor.feature@GetDoctorByIdNotFound') { id: '#(doctorId)' }

  @DoctorService
  @GetDoctorList
  Scenario: Get Doctor List
    * def doctorCreateResponse = call read('Doctor.feature@CreateDoctor')
    And match doctorCreateResponse.response.id == '#notnull'
    Given url doctor_url
    When method GET
    Then status 200
    And match each response.content[*] == '#notnull'
    And match each response.content[*] contains deep {id: '#number'}
    And match each response.content[*] contains deep {firstName: '#string'}
    And match each response.content[*] contains deep {lastName: '#string'}
    And match each response.content[*] contains deep {title: '#string'}
    And match each response.content[*] contains deep {speciality: '#string'}
    And match each response.content[*] contains deep {email: '#string'}
    And match each response.content[*] contains deep {phone: '#string'}
    And match each response.content[*] contains deep {department: '#string'}
    * call read('Doctor.feature@DeleteDoctor') { id: '#(doctorCreateResponse.response.id)'}

  @CreateDoctor
  @Ignore
  Scenario: Insert Doctor
    * def req = read('data/doctor_request.json')
    * req.firstName = RandomData.randomFirstName()
    * req.lastName = RandomData.randomLastName()
    * req.email = RandomData.randomEmail(req.firstName, req.lastName)
    * print req.email
    Given url doctor_url
    And request req
    When method POST
    Then status 201

  @CreateDoctorWithParams
  @Ignore
  Scenario: Insert Doctor
    * def req = read('data/doctor_request.json')
    * req.firstName = firstName
    * req.lastName = lastName
    * req.email = email
    Given url doctor_url
    And request req
    When method POST
    Then status 201

  @GetDoctorByIdNotFound
  @Ignore
  Scenario: Get Doctor By Id
    Given url doctor_url + '/' + id
    When method GET
    Then status 404
    And match response == '#notnull'
    And match response contains deep {code: '#number'}
    And match response contains deep {status: '#string'}
    And match response contains deep {description: '#string'}

  @GetDoctorById
  @Ignore
  Scenario: Get Doctor By Id
    Given url doctor_url + '/' + id
    When method GET
    Then status 200
    And match response == '#notnull'
    And match response contains deep {id: '#number'}
    And match response contains deep {firstName: '#string'}
    And match response contains deep {lastName: '#string'}
    And match response contains deep {speciality: '#string'}
    And match response contains deep {email: '#string'}
    And match response contains deep {title: '#string'}
    And match response contains deep {phone: '#string'}
    And match response contains deep {department: '#string'}


  @UpdateDoctor
  @Ignore
  Scenario: Update Doctor
    * def req = read('data/doctor_request.json')
    * req.firstName = firstName
    * req.lastName = lastName
    * req.email = email
    Given url doctor_url + '/' + id
    And request req
    When method PUT
    Then status 200

  @DeleteDoctor
  @Ignore
  Scenario: Delete Doctor
    Given url doctor_url + '/' + id
    When method DELETE
    Then status 204