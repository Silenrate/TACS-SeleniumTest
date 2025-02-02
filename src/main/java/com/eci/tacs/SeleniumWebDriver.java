package com.eci.tacs;

import com.eci.tacs.drivers.Drivers;
import com.eci.tacs.testers.Tester;
import com.eci.tacs.testers.TesterImpl;


public class SeleniumWebDriver {
    public static void main(String[] args) throws Exception {
        Tester tester = new TesterImpl("http://ecibrary.herokuapp.com/");
        String username = "santiago.aponte@mail.escuelaing.edu.co";
        String password = "invitado";
        //Por defecto fue realizado con Microsoft Edge, para mirar los demás drivers verificar enumeración de Drivers
        Drivers driver = Drivers.EDGE;
        tester.setUpDriver(driver);
        tester.login(username, password);
        //El amount debe ser menor que 10 para esta implementación
        tester.search("Economia", 5);
        tester.reload();
        tester.addReserva(username);
        tester.reviewPastReserve();
        tester.closeSession();
        tester.incorrectLogin("xxxx", "invitado");
        tester.login("frasica", "123");
        tester.alterResourceState("2928");
        tester.close();
        tester.showResults();
        tester.writeResults();
    }
}

