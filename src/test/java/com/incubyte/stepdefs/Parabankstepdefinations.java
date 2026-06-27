package com.incubyte.stepdefinitions;

import com.incubyte.pages.AccountOverviewPage;
import com.incubyte.pages.HomePage;
import com.incubyte.pages.RegistrationPage;
import com.incubyte.utils.PlaywrightManager;
import com.incubyte.utils.TestDataGenerator;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class ParaBankStepDefinitions {

    private Page page;
    private HomePage homePage;
    private RegistrationPage registrationPage;
    private AccountOverviewPage accountOverviewPage;
    private Map<String, String> userData;

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("\n----- Starting Scenario: " + scenario.getName() + " -----");
        PlaywrightManager.initBrowser();
        page = PlaywrightManager.getPage();
        homePage            = new HomePage(page);
        registrationPage    = new RegistrationPage(page);
        accountOverviewPage = new AccountOverviewPage(page);
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("----- Finished Scenario: " + scenario.getName()
                + " | Status: " + scenario.getStatus() + " -----\n");

        // Capture screenshot on failure
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot();
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }
        PlaywrightManager.closeBrowser();
    }

    // ─────────────────────────────── GIVEN ───────────────────────────────

    @Given("I am on the ParaBank homepage")
    public void iAmOnTheParaBankHomepage() {
        homePage.navigateTo();
        System.out.println("[Step] Navigated to ParaBank homepage.");
    }

    @Given("I have a registered account on ParaBank")
    public void iHaveARegisteredAccountOnParaBank() {
        // Register a fresh user for isolated login tests
        homePage.clickRegister();
        userData = TestDataGenerator.generateUserData();
        registrationPage.fillRegistrationForm(userData);
        registrationPage.submitForm();

        boolean registered = registrationPage.isRegistrationSuccessful();
        if (!registered) {
            throw new RuntimeException("Pre-condition failed: Could not register user.");
        }
        System.out.println("[Step] Pre-condition: Registered user - " + userData.get("username"));

        // Log out so we can test fresh login
        homePage.logout();
        homePage.navigateTo();
    }

    // ─────────────────────────────── WHEN ────────────────────────────────

    @When("I navigate to the registration page")
    public void iNavigateToTheRegistrationPage() {
        homePage.clickRegister();
        System.out.println("[Step] Navigated to registration page.");
    }

    @When("I fill in the registration form with valid user details")
    public void iFillInTheRegistrationFormWithValidUserDetails() {
        userData = TestDataGenerator.generateUserData();
        registrationPage.fillRegistrationForm(userData);
        System.out.println("[Step] Filled registration form.");
    }

    @When("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        registrationPage.submitForm();
        System.out.println("[Step] Submitted registration form.");
    }

    @When("I sign in with valid credentials")
    public void iSignInWithValidCredentials() {
        userData = TestDataGenerator.getRegisteredUser();
        homePage.login(userData.get("username"), userData.get("password"));
        System.out.println("[Step] Signed in with: " + userData.get("username"));
    }

    @When("I sign out and return to the homepage")
    public void iSignOutAndReturnToTheHomepage() {
        homePage.logout();
        homePage.navigateTo();
        System.out.println("[Step] Signed out and returned to homepage.");
    }

    @When("I sign in with the newly registered credentials")
    public void iSignInWithTheNewlyRegisteredCredentials() {
        userData = TestDataGenerator.getRegisteredUser();
        homePage.login(userData.get("username"), userData.get("password"));
        System.out.println("[Step] Signed in with newly registered user: " + userData.get("username"));
    }

    // ─────────────────────────────── THEN ────────────────────────────────

    @Then("I should see a confirmation that the account was created successfully")
    public void iShouldSeeAConfirmationThatTheAccountWasCreatedSuccessfully() {
        boolean success = registrationPage.isRegistrationSuccessful();
        if (!success) {
            String error = registrationPage.getErrorMessage();
            throw new AssertionError("Registration failed. Error: " + error);
        }
        System.out.println("[Step] ✓ Account registration confirmed.");
    }

    @Then("I should be logged in and see the account overview")
    public void iShouldBeLoggedInAndSeeTheAccountOverview() {
        boolean loggedIn = accountOverviewPage.isLoggedIn();
        if (!loggedIn) {
            throw new AssertionError("Login failed - user is not authenticated.");
        }
        System.out.println("[Step] ✓ User is logged in successfully.");
    }

    @And("I should print the account balance displayed on the page")
    public void iShouldPrintTheAccountBalanceDisplayedOnThePage() {
        accountOverviewPage.printAccountBalances();
        System.out.println("[Step] ✓ Account balance printed to console.");
    }
}