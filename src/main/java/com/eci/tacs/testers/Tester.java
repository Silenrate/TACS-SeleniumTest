package com.eci.tacs.testers;

public interface Tester {
    void login(String username, String password);
    void search(String value, int amount);
    void showResults();
    void close();
}
