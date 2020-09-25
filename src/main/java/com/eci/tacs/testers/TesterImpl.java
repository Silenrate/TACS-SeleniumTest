package com.eci.tacs.testers;

import com.eci.tacs.drivers.Drivers;
import com.eci.tacs.notifiers.Notifier;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class TesterImpl implements Tester {

    private final String url;
    private WebDriver webDriver;

    public TesterImpl(String url) {
        this.url = url;
    }

    @Override
    public void setUpDriver(Drivers driver) {
        webDriver = driver.getWebDriver();
    }

    @Override
    public void login(String username, String password) throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
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
        Notifier.addNotification("Trying to open session as " + username);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String actualUrl = webDriver.getCurrentUrl();
        if (actualUrl.equals("http://ecibrary.herokuapp.com/faces/comunidadInicio.xhtml")) {
            Notifier.addNotification(String.format("Open session success, actual url: %s", actualUrl));
        } else {
            Notifier.addNotification(String.format("Open session failed, actual url: %s", actualUrl));
        }

    }

    @Override
    public void search(String value, int amount) throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
        Notifier.addNotification(String.format("PARAMETER SEARCH TEST OF VALUE \"%s\" OVER %d RESULTS :", value, amount));
        //Se pone el valor a buscar, este elemento se tarda en cargar y por eso se usa el método element
        WebElement webElement = element(By.xpath("//*[@id=\"comunidadTable_filter\"]/label/input"));
        webElement.sendKeys(value);
        //Miramos si los nombres de los resultados poseen el valor de la búsqueda
        int correctName = 0;
        for (int i = 1; i <= amount; i++) {
            String xpath = "//*[@id=\"comunidadTable\"]/tbody/tr[" + i + "]/td[2]";
            webElement = webDriver.findElement(By.xpath(xpath));
            String nombre = webElement.getText();
            if (nombre.contains(value)) {
                Notifier.addNotification(String.format("The search number %d has the name %s , this includes the search value %s", i, nombre, value));
                correctName++;
            } else {
                Notifier.addNotification(String.format("The search number %d has the name %s , this not includes the search value %s", i, nombre, value));
            }
        }
        int percentOfCorrectCases = (correctName / amount) * 100;
        Notifier.addNotification(String.format("The %d%% of checked cases were correct %n", percentOfCorrectCases));
    }

    @Override
    public void addReserva(String username) throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
        Notifier.addNotification("EQUIPMENT RESERVATION TEST :");
        //Va a la sección de ver recursos disponibles
        WebElement elm = element(By.xpath("/html/body/aside/nav/ul/li[2]"));
        //Ingresa a la sección ver recursos disponibles
        elm.click();
        //selecciona la el botón de reserva para el primer item
        elm = element(By.xpath("/html/body/section/div/div[1]/div[2]/div/table/tbody/tr[1]/td[7]"));
        //Da click para habilitar el botón
        elm.click();
        //Al cargar la vista selecciona el botón para realizar la reserva
        elm = element(By.xpath("/html/body/section/div/div[1]/div[2]/div/table/tbody/tr[2]/td/center/form/button"));
        elm.click();
        //Selecciona en el calendario la fecha 07/09/2020
        elm = element(By.xpath("html/body/section/form/table/tbody/tr/td/div/div/div[2]/div/table/tbody/tr/td/div/div/div[2]/div[1]/table/tbody/tr/td[2]"));
        //Abre el model de registro de la reserva
        elm.click();
        //Ingresa el nombre de la reserva
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/section/form/div[2]/div[2]/table/tbody/tr[1]/td[2]/input")));
        elm.click();
        elm.sendKeys("Test-App");
        //Selecciona la cantidad de recursos a reservar(Libros)
        elm = webDriver.findElement(By.xpath("html/body/section/form/div[2]/div[2]/table/tbody/tr[5]/td[2]/select"));
        elm.sendKeys("1");
        //Ejecuta la reserva
        elm = webDriver.findElement(By.xpath("/html/body/section/form/div[2]/div[2]/table/tbody/tr[6]/td[2]/button"));
        elm.click();
        Notifier.addNotification("Attempting to reserve the resource...");
        //Actualiza la pagina
        webDriver.navigate().refresh();
        //Verifica que dentro de el calendario se encuentre la reserva
        elm = element(By.xpath("/html/body/section/form/table/tbody/tr/td/div/div/div[2]/div/table/tbody/tr/td/div/div/div[3]/div[2]/table/tbody/tr/td[4]/a/div/span[2]"));
        String text = elm.getText();
        if (text.contains(username)) {
            Notifier.addNotification(String.format("The user %s make successfully the registry of the resource %n", username));
        } else {
            Notifier.addNotification("The resource was not registered \n");
        }
    }

    @Override
    public void showResults() {
        Notifier.printNotifications();
    }

    @Override
    public void writeResults() throws IOException {
        Notifier.writeNotifications();
    }

    @Override
    public void close() throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
        webDriver.close();
    }

    @Override
    public void reload() throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
        webDriver.get(url + "/faces/comunidadInicio.xhtml");
    }

    public WebElement element(By locator) {
        int timeoutLimitSeconds = 200;
        WebDriverWait wait = new WebDriverWait(webDriver, timeoutLimitSeconds);
        try {
            //Espera a que el tiempo acordado hasta que elemento aparezca en la página
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new NoSuchElementException(locator.toString());
        }
        return webDriver.findElement(locator);
    }

    @Override
    public void incorrectLogin(String username, String password) throws TestException {
        Notifier.addNotification("INVALID LOGIN TEST :");
        login(username, password);
        String expectedValue = "Unknown account";
        //Se revisa el mensaje de error
        WebElement elm = element(By.xpath("//*[@id=\"frm:messages\"]/div/ul/li/span[1]"));
        String errorMessage = elm.getText();
        Notifier.addNotification("Error Message Expected: " + expectedValue);
        Notifier.addNotification("Error Message Obtained: " + errorMessage);
        if (errorMessage.equals(expectedValue)) {
            Notifier.addNotification("Invalid Login Test Successful");
        } else {
            Notifier.addNotification("Invalid Login Test Failed");
        }

    }

    @Override
    public void closeSession() {
        //Se selecciona el menu de ajustes
        WebElement elm = element(By.xpath("/html/body/aside/nav/ul/li[4]"));
        elm.click();
        //Se elige la opción de logout
        elm = element(By.xpath("/html/body/aside/nav/ul/li[4]/ul"));
        elm.click();
        Notifier.addNotification(String.format("Close session, actual url: %s %n", webDriver.getCurrentUrl()));
    }
}
