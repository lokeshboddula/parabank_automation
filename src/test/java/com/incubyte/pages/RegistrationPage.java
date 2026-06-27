package com.incubyte.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Map;

public class RegistrationPage {

    private final Page page;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator addressInput;
    private final Locator cityInput;
    private final Locator stateInput;
    private final Locator zipCodeInput;
    private final Locator phoneInput;
    private final Locator ssnInput;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator confirmPasswordInput;
    private final Locator registerButton;
    private final Locator successHeader;
    private final Locator errorMessage;

    public RegistrationPage(Page page) {
        this.page               = page;
        this.firstNameInput     = page.locator("input[id='customer.firstName']");
        this.lastNameInput      = page.locator("input[id='customer.lastName']");
        this.addressInput       = page.locator("input[id='customer.address.street']");
        this.cityInput          = page.locator("input[id='customer.address.city']");
        this.stateInput         = page.locator("input[id='customer.address.state']");
        this.zipCodeInput       = page.locator("input[id='customer.address.zipCode']");
        this.phoneInput         = page.locator("input[id='customer.phoneNumber']");
        this.ssnInput           = page.locator("input[id='customer.ssn']");
        this.usernameInput      = page.locator("input[id='customer.username']");
        this.passwordInput      = page.locator("input[id='customer.password']");
        this.confirmPasswordInput = page.locator("input[id='repeatedPassword']");
        this.registerButton     = page.locator("input[value='Register']");
        this.successHeader      = page.locator("h1.title");
        this.errorMessage       = page.locator(".error");
    }

    public void fillRegistrationForm(Map<String, String> userData) {
        firstNameInput.fill(userData.get("firstName"));
        lastNameInput.fill(userData.get("lastName"));
        addressInput.fill(userData.get("address"));
        cityInput.fill(userData.get("city"));
        stateInput.fill(userData.get("state"));
        zipCodeInput.fill(userData.get("zipCode"));
        phoneInput.fill(userData.get("phone"));
        ssnInput.fill(userData.get("ssn"));
        usernameInput.fill(userData.get("username"));
        passwordInput.fill(userData.get("password"));
        confirmPasswordInput.fill(userData.get("password"));
        System.out.println("[RegistrationPage] Form filled for user: " + userData.get("username"));
    }

    public void submitForm() {
        registerButton.click();
        page.waitForLoadState();
        System.out.println("[RegistrationPage] Registration form submitted.");
    }

    public boolean isRegistrationSuccessful() {
        try {
            page.waitForSelector("h1.title", new Page.WaitForSelectorOptions().setTimeout(8000));
            String headingText = successHeader.textContent();
            boolean success = headingText != null && headingText.contains("Welcome");
            System.out.println("[RegistrationPage] Registration success: " + success + " | Heading: " + headingText);
            return success;
        } catch (Exception e) {
            System.out.println("[RegistrationPage] Success header not found: " + e.getMessage());
            return false;
        }
    }

    public String getErrorMessage() {
        if (errorMessage.isVisible()) {
            return errorMessage.textContent();
        }
        return "";
    }

    public boolean isOnRegistrationPage() {
        return page.url().contains("register");
    }
}