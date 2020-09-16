package com.eci.tacs;

import com.eci.tacs.drivers.Drivers;
import com.eci.tacs.testers.TestException;
import com.eci.tacs.testers.Tester;
import com.eci.tacs.testers.TesterImpl;

public class ReservaApp {
    public static void main(String[] args) throws TestException {
        Tester tester = new TesterImpl("http://ecibrary.herokuapp.com/");
        //Por defecto lo hice con Edge, para mirar los demás drivers verificar enumeración de Drivers
        tester.setUpDriver(Drivers.EDGE);
        tester.login("santiago.aponte@mail.escuelaing.edu.co", "invitado");
        tester.addReserva();
    }
}
