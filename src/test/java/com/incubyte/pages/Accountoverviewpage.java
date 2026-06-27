package com.incubyte.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class Accountoverviewpage {

    private final Page page;

    // Locators
    private final Locator pageHeading;
    private final Locator accountRows;
    private final Locator totalBalance;
    private final Locator accountOverviewLink;
    private final Locator welcomeMessage;

    public Accountoverviewpage(Page page) {
        this.page                = page;
        this.pageHeading         = page.locator("h1.title");
        this.accountRows         = page.locator("table#accountTable tbody tr");
        this.totalBalance        = page.locator("#accountTable tbody tr:last-child td:nth-child(2)");
        this.accountOverviewLink = page.locator("a[href*='overview.htm']");
        this.welcomeMessage      = page.locator("#leftPanel p.smallText");
    }

    public boolean isLoggedIn() {
        try {
            page.waitForSelector("a[href*='logout']",
                    new Page.WaitForSelectorOptions().setTimeout(8000));
            System.out.println("[AccountOverviewPage] User is logged in. URL: " + page.url());
            return true;
        } catch (Exception e) {
            System.out.println("[AccountOverviewPage] Login verification failed: " + e.getMessage());
            return false;
        }
    }

    public void navigateToAccountOverview() {
        try {
            if (accountOverviewLink.isVisible()) {
                accountOverviewLink.click();
                page.waitForLoadState();
                // ADD this wait — the table loads via a small delay
                page.waitForSelector("table#accountTable",
                        new Page.WaitForSelectorOptions().setTimeout(10000));
            }
        } catch (Exception e) {
            System.out.println("[AccountOverviewPage] Could not navigate to overview: " + e.getMessage());
        }
    }

    public void printAccountBalances() {
        System.out.println("\n======================================================");
        System.out.println("         PARABANK - POST-LOGIN ACCOUNT SUMMARY        ");
        System.out.println("======================================================");

        // Print welcome / username if visible
        try {
            if (welcomeMessage.isVisible()) {
                System.out.println("Logged-in User : " + welcomeMessage.textContent().trim());
            }
        } catch (Exception ignored) {}

        // Navigate to account overview if not already there
        navigateToAccountOverview();

        // Wait for the table
        try {
            page.waitForSelector("table#accountTable",
                    new Page.WaitForSelectorOptions().setTimeout(8000));
        } catch (Exception e) {
            System.out.println("[AccountOverviewPage] Account table not found: " + e.getMessage());
        }

        // Print each account row
        List<String> balances = new ArrayList<>();
        int count = accountRows.count();

        System.out.printf("%-20s %-20s %-20s%n", "Account Number", "Balance", "Available Amount");
        System.out.println("------------------------------------------------------");

        for (int i = 0; i < count; i++) {
            Locator row = accountRows.nth(i);
            List<Locator> cells = row.locator("td").all();
            if (cells.size() >= 3) {
                String accountNum = cells.get(0).textContent().trim();
                String balance    = cells.get(1).textContent().trim();
                String available  = cells.get(2).textContent().trim();

                // Skip empty/footer rows
                if (!accountNum.isEmpty() && !accountNum.equalsIgnoreCase("Account")) {
                    System.out.printf("%-20s %-20s %-20s%n", accountNum, balance, available);
                    balances.add(balance);
                }
            }
        }

        // Print total
        try {
            String total = totalBalance.textContent().trim();
            System.out.println("------------------------------------------------------");
            System.out.println("Total Balance  : " + total);
        } catch (Exception e) {
            System.out.println("[AccountOverviewPage] Could not read total balance.");
        }

        System.out.println("======================================================\n");

        if (balances.isEmpty()) {
            System.out.println("[AccountOverviewPage] No account balance rows found in table.");
        }
    }

    public String getPageHeading() {
        try {
            return pageHeading.textContent().trim();
        } catch (Exception e) {
            return "";
        }
    }
}