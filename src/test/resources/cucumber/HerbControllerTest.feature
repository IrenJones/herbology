Feature: the test string can be retrieved
  Scenario: client makes call to test method :D
    When I GET from /herbs/test
    Then the response status code is 200
    And the client receives answer Hello