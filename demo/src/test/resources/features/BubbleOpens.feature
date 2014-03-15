Feature: Bubble opens

  Scenario: Open bubble
    Given the demo page
    When I focus the "company" field
    And I press F1
    Then it should display the help bubble
    And the bubble text should read "Fill the company field with the name of your company if your address is for official company business."

  Scenario: Open bubble by clicking icon
    Given the demo page
    And the "Wrapped fields" tab is open
    When I click the icon of the "company" field
    Then it should display the help bubble
    And the bubble text should read "Fill the company field with the name of your company if your address is for official company business."