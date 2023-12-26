@AddUser
Feature: Adding new user to the system

  Scenario: Adding a new valid user to the system.
    Given I have valid user input data available
    When I add the user to the system
    Then the id of the user is returned

  Scenario: Adding a new invalid user to the system.
    Given I have invalid user input data available
    When I add the user to the system
    Then I get an exception