Feature: Verify the Bpi's in Coin Desk Api

  Scenario: Verify 3 BPi is present in the response
    When I call create API <endpoint>
      | https://api.coindesk.com/v1/bpi/currentprice.json |
    Then I validate the status code is 200
    And I validate below BPI
      |USD|
      |GBP|
      |EUR|
    And I validate description "British Pound Sterling" for bpi  "GPB"