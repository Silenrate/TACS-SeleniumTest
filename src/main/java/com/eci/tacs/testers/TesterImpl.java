package com.eci.tacs.testers;

import com.eci.tacs.drivers.Drivers;
import com.eci.tacs.notifiers.Notifier;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    }

    @Override
    public void search(String value, int amount) throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
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
    public void close() throws TestException {
        if (webDriver == null) throw new TestException(TestException.DRIVER_NOT_SETUP);
        webDriver.close();
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

    public void addReserva(){
        System.out.println("Add reserva");
        WebElement elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/aside/nav/ul/li[2]")));
        //WebElement elm = webDriver.findElement(By.xpath("/html/body/aside/nav/ul/li[2]"));
        elm.click();
        //WebElement elm2 = webDriver.findElement(By.xpath("/html/body/aside/nav/ul/li[2]/ul/li"));
        //WebElement elm2 = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/aside/nav/ul/li[2]/ul/li")));
        //elm2.click();
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div/div[1]/div[2]/div/table/tbody/tr[1]/td[7]")));
        //elm = webDriver.findElement(By.xpath("/html/body/section/div/div[1]/div[2]/div/table/tbody/tr[1]/td[7]/center"));
        elm.click();
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div/div[1]/div[2]/div/table/tbody/tr[2]/td/center/form/button")));
        //elm = webDriver.findElement(By.xpath("//*[@id=\"j_idt39:536:j_idt41:boton1\"]"));
        elm.click();
        ///html/body/section/form/table/tbody/tr/td/div/div/div[2]/div/table/tbody/tr/td/div/div/div[2]/div[1]/table/tbody/tr/td[2]
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/section/form/table/tbody/tr/td/div/div/div[2]/div/table/tbody/tr/td/div/div/div[2]/div[1]/table/tbody/tr/td[2]")));
        elm.click();
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/section/form/table/tbody/tr/td/div/div/div[2]/div/table/tbody/tr/td/div/div/div[2]/div[1]/table/tbody/tr/td[2]")));
        //elm.click();
        ///html/body/section/form/div[2]/div[2]/table/tbody/tr[1]/td[2]/input
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/section/form/div[2]/div[2]/table/tbody/tr[1]/td[2]/input")));
        elm.click();
        elm.sendKeys("Test-App");
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/section/form/div[2]/div[2]/table/tbody/tr[5]/td[2]/select")));
        elm.sendKeys("1");
        /////html/body/section/form/div[2]/div[2]/table/tbody/tr[5]/td[2]/select

        elm = webDriver.findElement(By.xpath("/html/body/section/form/div[2]/div[2]/table/tbody/tr[6]/td[2]/button"));
        elm.click();
        webDriver.navigate().refresh();
        ////*[@id="j_idt39:schedule_container"]/div[2]/div/table/tbody/tr/td/div/div/div[3]/div[2]/table/tbody/tr/td[4]/a/div/span[2]
        elm = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/form/table/tbody/tr/td/div/div/div[2]/div/table/tbody/tr/td/div/div/div[3]/div[2]/table/tbody/tr/td[4]/a/div/span[2]")));
        String text = elm.getText();
        System.out.println(text);
        System.out.println(text.contains("santiago.aponte@mail.escuelaing.edu.co"));
        webDriver.close();
    }
}
