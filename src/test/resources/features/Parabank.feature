@parabank
Feature: ParaBank Account Registration and Sign In

  As a new user
  I want to register an account on ParaBank
  So that I can sign in and view my account balance

  Background:
    Given I am on the ParaBank homepage

  @registration @smoke
  Scenario: Successfully register a new account on ParaBank
    When I navigate to the registration page
    And I fill in the registration form with valid user details
    And I submit the registration form
    Then I should see a confirmation that the account was created successfully

  @login @smoke
  Scenario: Successfully sign in with a registered account
    Given I have a registered account on ParaBank
    When I sign in with valid credentials
    Then I should be logged in and see the account overview
    And I should print the account balance displayed on the page

  @end-to-end @smoke
  Scenario: Register a new account and sign in to view account balance
    When I navigate to the registration page
    And I fill in the registration form with valid user details
    And I submit the registration form
    Then I should see a confirmation that the account was created successfully
    When I sign out and return to the homepage
    And I sign in with the newly registered credentials
    Then I should be logged in and see the account overview
    And I should print the account balance displayed on the page
