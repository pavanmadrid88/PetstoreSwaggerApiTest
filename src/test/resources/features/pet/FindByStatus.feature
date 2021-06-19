Feature: The pet findByStatus service finds pet by status


  @findByStatus
  Scenario Outline: Get pet by Status

    Given a valid pet status "<petStatus>"
    When I retrieve pets by status "<petStatus>"
    Then I should see response status code as "<statusCode>"
    And  response time should be less than "<expectedResponseTime>" milliseconds
    And I should be able to see details of pet "<petName>"

    Examples:
      | petName | petStatus |  | statusCode | expectedResponseTime |
      | Dogs    | available |  | 200        | 4000                 |
      | Lions   | available |  | 200        | 4000                 |





