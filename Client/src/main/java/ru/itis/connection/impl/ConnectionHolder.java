package ru.itis.connection.impl;

import ru.itis.utils.exceptions.ConnectionNotInitializedException;

public class ConnectionHolder {
    private static ClientConnectionThread connection;

    public static ClientConnectionThread getConnection() throws ConnectionNotInitializedException {
        if (connection == null) {
            throw new ConnectionNotInitializedException("Connection lost");
        }
        return connection;
    }

    public static void initializeConnection() {
        // static because we want singleton
        if (connection == null) {
            connection = new ClientConnectionThread();
        }
    }

    public static void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
        }
        // if user open and then immediately close window
    }
}
