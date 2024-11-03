package com.pages;

import com.microsoft.playwright.Page;
import com.base.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(Page page) {
        super(page);
    }

    public void navigateToLogin() {
        page.navigate("https://practicetestautomation.com/practice-test-login/");
    }

    public void login(String username, String password) {
        page.fill("[name='username']", username);
        page.fill("[name='password']", password);
        page.click("[id='submit']");
    }

    public String getSuccessMessage() {
        // Add a wait to make sure the page is loaded
        page.waitForLoadState();
        // Get the successful text in the webpage
        return page.textContent(".post-title");
    }

    public boolean isLoginSuccessful() {
        page.waitForLoadState();
        return page.isVisible(".post-content strong");
    }
}
