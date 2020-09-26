package com.eci.tacs.testers;

import com.eci.tacs.drivers.Drivers;

import java.io.IOException;

public interface Tester {
    void setUpDriver(Drivers driver);

    void login(String username, String password) throws TestException;

    void search(String value, int amount) throws TestException;

    void addReserva(String username) throws TestException;

    void showResults();

    void writeResults() throws IOException;

    void close() throws TestException;

    void reload() throws TestException;

    void incorrectLogin(String username, String password) throws TestException;

    void closeSession();

    void reviewPastReserve() throws TestException;

    void alterResourceState(String resourceId) throws TestException;
}
