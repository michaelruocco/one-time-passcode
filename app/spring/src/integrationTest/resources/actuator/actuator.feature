Feature: Actuator Requests

  Background:
    * url baseUrl + "/actuator"

  Scenario: Get info - Returns app information
    Given path "/info"
    When method GET
    Then status 200
    And match response.app ==
      """
      {
        "name": "idv-one-time-passcode"
      }
      """

  Scenario: Get info - Returns build information
    Given path "/info"
    When method GET
    Then status 200
    And match response.build ==
      """
      {
        "artifact": "idv-one-time-passcode-spring-app",
        "name": "idv-one-time-passcode-spring-app",
        "time": "#notnull",
        "version": "#notnull",
        "group": "com.github.michaelruocco.idv"
      }
      """

  Scenario: Get info - Returns system properties
    Given path "/info"
    When method GET
    Then status 200
    * def systemProperties = response.systemProperties
    And match systemProperties contains { "server.port": "#present" }
    And match systemProperties contains { "environment": "idv-local" }
    And match systemProperties contains { "spring.data.mongodb.uri": "hidden" }
    And match systemProperties contains { "aws.sns.endpoint.uri": "#present" }

  Scenario: Get health - Returns successfully
    Given path "/health"
    When method GET
    Then status 200
    And match response contains { "status": "UP" }
