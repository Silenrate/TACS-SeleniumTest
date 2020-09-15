package com.eci.tacs;

import com.eci.tacs.testers.Tester;
import com.eci.tacs.testers.TesterImpl;

public class SeleniumWebDriver {
    public static void main(String[] args) {
        Tester tester = new TesterImpl("http://ecibrary.herokuapp.com/");
        tester.login("santiago.aponte@mail.escuelaing.edu.co", "invitado");
        //El amount debe ser menor que 10
        tester.search("Economia", 5);
        tester.close();
        tester.showResults();
    }
}

