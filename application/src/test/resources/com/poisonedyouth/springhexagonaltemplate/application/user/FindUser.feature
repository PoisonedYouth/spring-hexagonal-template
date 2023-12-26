@FindUser
Feature: Finding user in the system

  Scenario: Finding all existing user in the system
    Given the following users exist:
      | John      | Doe       | Main Street   | 12A           | Los Angeles   | 12345   | United States | USA |
      | Jane      | Smith     | Broad Avenue  | 15B           | New York      | 67890   | United States | USA |
      | Michael   | Johnson   | High Street   | 20C           | Chicago       | 34567   | United States | USA |
      | Emily     | Williams  | Park Lane     | 7D            | San Francisco | 78901   | United States | USA |
    When I request for all user
    Then there are 4 users returned