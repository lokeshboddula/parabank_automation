package com.incubyte.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;

    // Locators
    private final Locator registerLink;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator logoutLink;

    private static final String BASE_URL = "https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC";

    public HomePage(Page page) {
        this.page = page;
        this.registerLink  = page.locator("a[href*='register']");
        this.usernameInput = page.locator("[name='username']");
        this.passwordInput = page.locator("[name='password']");
        this.loginButton   = page.locator("input[value='Log In']");
        this.logoutLink = page.locator("a[href*='logout.htm']");
    }

    public void navigateTo() {
        page.navigate(BASE_URL);
        page.waitForLoadState();
        System.out.println("[HomePage] Navigated to: " + BASE_URL);
    }

    public void clickRegister() {
        registerLink.click();
        page.waitForLoadState();
        System.out.println("[HomePage] Clicked Register link.");
    }

    public void login(String username, String password) {
        System.out.println("[HomePage] Attempting to log in with username: " + username);
        System.out.println("[HomePage] Attempting to log in with password: " + password);
        System.out.println("[HomePage] is username visible " + usernameInput.isVisible());
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
        page.waitForLoadState();
        System.out.println("[HomePage] Logging in as: " + username);
    }

    public void logout() {
        if (logoutLink.isVisible()) {
            logoutLink.click();
            page.waitForLoadState();
            System.out.println("[HomePage] Logged out.");
        }
    }

    public boolean isLoaded() {
        return page.url().contains("parabank");
    }
}