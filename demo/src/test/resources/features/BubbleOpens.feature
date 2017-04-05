Feature: Bubble opens

  Scenario: Open bubble
    Given the demo page
    When I focus the "company" field
    And I press F1
    Then it should display the help bubble
    And the bubble text should read "Fill the company field with the name of your company if your address is for official company business."
