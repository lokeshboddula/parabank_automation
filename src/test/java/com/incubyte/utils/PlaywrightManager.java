package com.incubyte.utils;

import com.microsoft.playwright.*;

public class PlaywrightManager {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private PlaywrightManager() {}

    public static void initBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(100)
        );
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1440, 900)
        );
        page = context.newPage();
        System.out.println("[PlaywrightManager] Browser launched successfully.");
    }

    public static Page getPage() {
        if (page == null) {
            initBrowser();
        }
        return page;
    }

    public static void closeBrowser() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        page = null;
        context = null;
        browser = null;
        playwright = null;
        System.out.println("[PlaywrightManager] Browser closed.");
    }
}