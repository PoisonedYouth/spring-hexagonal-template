@AddUser
Feature: Adding new user to the system

  Scenario: Adding a new valid user to the system.
    Given I have valid user input data available
    When I add the user to the system
    Then the id of the user is returned
