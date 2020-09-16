package com.eci.tacs;

import com.eci.tacs.drivers.Drivers;
import com.eci.tacs.testers.TestException;
import com.eci.tacs.testers.Tester;
import com.eci.tacs.testers.TesterImpl;

public class SeleniumWebDriver {
    public static void main(String[] args) throws TestException {
        Tester tester = new TesterImpl("http://ecibrary.herokuapp.com/");
        String username = "santiago.aponte@mail.escuelaing.edu.co";
        String password = "invitado";
        //Por defecto lo hice con Edge, para mirar los demás drivers verificar enumeración de Drivers
        Drivers driver = Drivers.EDGE;
        tester.setUpDriver(driver);
        tester.login(username, password);
        //El amount debe ser menor que 10 para esta implementación
        tester.search("Economia", 5);
        tester.close();
        tester.setUpDriver(driver);
        tester.login(username, password);
        tester.addReserva(username);
        tester.close();
        tester.showResults();
    }
}

