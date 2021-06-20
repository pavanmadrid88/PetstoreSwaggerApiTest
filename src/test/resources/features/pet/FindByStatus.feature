Feature: The pet findByStatus service finds pet by status


  @findByStatus-valid
  Scenario Outline: Get pet by valid Status

    Given a valid pet status "<petStatus>"
    When I retrieve pets by status "<petStatus>"
    Then I should see response status code as "<statusCode>"
    And  response time should be less than "<expectedResponseTime>" milliseconds
    And I should be able to see details of pet "<petName>"

    Examples:
      | petName | petStatus |  | statusCode | expectedResponseTime |
      | Dogs    | available |  | 200        | 2000                 |
      | Lions   | available |  | 200        | 2000                 |
      | Lions   | pending   |  | 200        | 2000                 |
      | Lions   | sold      |  | 200        | 2000                 |


  @findByStatus-invalid
  Scenario Outline: Get pet by an invalid Status

    Given an invalid pet status "<petStatus>"
    When I retrieve pets by status "<petStatus>"
    Then I should see response status code as "<statusCode>"
    And I should see error "<errorMessage>" in the response

    Examples:

      | petStatus | statusCode | errorMessage |
      | invalid   | 400        | Input error  |
