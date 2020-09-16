package com.eci.tacs.testers;

import com.eci.tacs.drivers.Drivers;

public interface Tester {
    void setUpDriver(Drivers driver);

    void login(String username, String password) throws TestException;

    void search(String value, int amount) throws TestException;

    void showResults();

    void close() throws TestException;
}
