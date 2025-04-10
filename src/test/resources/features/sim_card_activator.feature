Feature: SIM card activation

  Scenario: Successful SIM card activation
    Given a SIM card with ICCID "1255789453849037777" and email "user@example.com"
    When I activate the SIM card
    Then the activation should be successful
    And the activation status for SIM card ID 1 should be "true"

  Scenario: Failed SIM card activation
    Given a SIM card with ICCID "8944500102198304826" and email "fail@example.com"
    When I activate the SIM card
    Then the activation should fail
    And the activation status for SIM card ID 2 should be "false"
