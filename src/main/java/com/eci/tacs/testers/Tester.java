package com.eci.tacs.testers;

public interface Tester {
    public void searchTest(String searchValue, String expectedValue);
    public void login(String username, String password);
}
