package com.stepDefinitions;

import com.microsoft.playwright.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import com.pages.LoginPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class LoginSteps {
    private Playwright playwright;
    private Browser browser;
    private LoginPage loginPage;
    private Properties properties;

    @Before
    public void setUp() throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
        }
    }

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        loginPage = new LoginPage(page);
        loginPage.navigateToLogin();
    }

    @When("the user enters valid credentials")
    public void theUserEntersValidCredentials() {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        System.out.println("username: " + username);
        loginPage.login(username, password);
    }

    @Then("the user should be logged in")
    public void theUserShouldBeLoggedIn() {

        String successMessage = loginPage.getSuccessMessage();
        assert successMessage.equals("Logged In Successfully");
        assertTrue(loginPage.isLoginSuccessful());
        browser.close();
        playwright.close();
    }
}
