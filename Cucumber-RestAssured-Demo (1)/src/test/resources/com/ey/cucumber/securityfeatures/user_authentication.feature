Feature: Test user authentication via API

  Scenario Outline: Authentication is successful with only a valid username and valid password

    Given User sets the base API request "<URL>"
    When User makes auth request with "<username>" and "<password>"
    Then The response should have "<statusCode>"

    Examples:
      |URL                  |username      |password |statusCode|
      |http://localhost:8080|administrator |admin    |200       |
      |http://localhost:8080|employee      |password |200       |
      |http://localhost:8080|employee      |badpass  |401       |
#      |http://testphp.vulnweb.com|test    |test    |200       |
#      |http://testphp.vulnweb.com|hello   |cucumber|302       |
#      |http://github.com         |hello   |cucumber|301       |