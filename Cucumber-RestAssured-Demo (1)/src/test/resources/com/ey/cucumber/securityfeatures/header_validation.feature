Feature: Test that security headers are present

  Scenario Outline: There is an XSS header set to 1 to prevent again XSS attacks

    Given User sets the base request "<URL>"
    And an admin token has been obtained
    When accessing this "<endpoint>"
    Then Then XSS "<header>" should be present

    Examples:
      |URL                       |endpoint                      |header                 |
      |http://localhost:8080     |/api/rooms                    |1; mode=block          |
#      |http://google.com         |                              |1                      |
#      |https://developer.mozilla.org/en-US/docs/Web/Tutorials | |1; mode=block          |

  Scenario Outline: There is an X Frame Options header set to 1 to prevent again XSS attacks

    Given User sets the base request "<URL>"
    When accessing this "<endpoint>"
    Then Then x-frame-options "<header>" should be present

    Examples:
      |URL                       |endpoint                      |header        |
      |http://localhost:8080     |/api/rooms                    |SAMEORIGIN    |
#      |http://testphp.vulnweb.com|/cart.php                     |DENY          |
#      |http://google.com         |                              |SAMEORIGIN    |
#      |https://developer.mozilla.org/en-US/docs/Web/Tutorials | |DENY          |