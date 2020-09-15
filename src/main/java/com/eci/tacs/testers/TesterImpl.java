package com.eci.tacs.testers;

import com.eci.tacs.drivers.Drivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TesterImpl implements Tester {

    public static final int DEFAULT_WAIT_TIME = 1000;
    private final String url;

    public TesterImpl(String url) {
        this.url = url;
    }

    public void search(String search, Drivers driver, String expectedValue) {
        WebDriver webDriver = driver.getWebDriver();
        webDriver.get(url);
        WebElement webElement = webDriver.findElement(By.id("searchInput"));
        webElement.click();
        webElement.sendKeys(search);
        webDriver.findElement(By.xpath("//button[@type='submit']")).submit();
        driverWait();
        String result = webDriver.findElement(By.id("firstHeading")).getText();
        if (result.equals(expectedValue)) {
            System.out.println("Test Success");
        } else {
            System.out.println("Test Failed, Expected Value: " + expectedValue + ", Actual Value: " + result);
        }
        webDriver.close();
    }

    private void driverWait() {
        try {
            Thread.sleep(DEFAULT_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchTest(String searchValue, String expectedValue) {
        search(searchValue, Drivers.EDGE, expectedValue);
    }

    @Override
    public void login(String username, String password) {
        WebDriver webDriver = Drivers.EDGE.getWebDriver();
        webDriver.get(url);
        //Se va a la pagina de login
        webDriver.findElement(By.xpath("//*[@id=\"hero\"]/div/div/div[2]/a[1]")).click();
        //Se ponen el nombre de usuario
        WebElement webElement = webDriver.findElement(By.id("frm:email"));
        webElement.sendKeys(username);
        //Se pone la contraseña
        webElement = webDriver.findElement(By.id("frm:j_idt7"));
        webElement.sendKeys(password);
        //Se realiza el login
        webDriver.findElement(By.xpath("//*[@id=\"frm:j_idt9\"]/span")).click();
    }
}
