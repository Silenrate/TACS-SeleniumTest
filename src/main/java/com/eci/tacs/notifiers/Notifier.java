package com.eci.tacs.notifiers;

import java.util.ArrayList;
import java.util.List;

public class Notifier {
    private static final List<String> notifications = new ArrayList<>();

    private Notifier() {
    }

    public static void addNotification(String notification) {
        notifications.add(notification);
    }

    public static void printNotifications() {
        notifications.forEach(System.out::println);
    }
}
