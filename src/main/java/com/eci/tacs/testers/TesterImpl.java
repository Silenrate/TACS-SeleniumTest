package com.eci.tacs.testers;

import com.eci.tacs.drivers.Drivers;
import com.eci.tacs.notifiers.Notifier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TesterImpl implements Tester {

    private final String url;
    private WebDriver webDriver;

    public TesterImpl(String url) {
        this.url = url;
    }

    private void driverWait(WebDriver webDriver, long waitTime) {
        System.out.println("Estoy esperando a que " + webDriver.getCurrentUrl() + " cargue completamente");
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            Notifier.addNotification("Error de espera en thread");
        }
    }

    @Override
    public void login(String username, String password) {
        //POR DEFECTO LO HICE CON EDGE
        webDriver = Drivers.EDGE.getWebDriver();
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

    @Override
    public void search(String value, int amount) {
        driverWait(webDriver, 30000);
        //Se pone el valor a buscar
        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"comunidadTable_filter\"]/label/input"));
        webElement.sendKeys(value);
        //Miramos si los nombres de los resultados poseen el valor de la búsqueda
        int correctName = 0;
        for (int i = 1; i <= amount; i++) {
            String xpath = "//*[@id=\"comunidadTable\"]/tbody/tr[" + i + "]/td[2]";
            webElement = webDriver.findElement(By.xpath(xpath));
            String nombre = webElement.getText();
            if (nombre.contains(value)) {
                Notifier.addNotification("El valor número " + i + " si posee el valor a buscar");
                correctName++;
            } else {
                Notifier.addNotification("El valor número " + i + " no posee el valor a buscar");
            }
        }
        int percentOfCorrectCases = (correctName / amount) * 100;
        Notifier.addNotification(String.format("El %d%% de los casos revisados fueron correctos", percentOfCorrectCases));
    }

    @Override
    public void showResults() {
        Notifier.printNotifications();
    }

    @Override
    public void close() {
        if (webDriver != null) webDriver.close();
    }
}
