package ru.itis.connection.impl;

import ru.itis.utils.exceptions.ConnectionNotInitializedException;

public class ConnectionHolder {
    private static ClientConnectionImpl connection;
    private static String playerUsername;

    public static ClientConnectionImpl getConnection() throws ConnectionNotInitializedException {
        if (connection == null) {
            throw new ConnectionNotInitializedException("Unknown connection");
        }
        return connection;
    }

    public void closeConnection() throws Exception {
        connection.close();
    }

    public void initializeConnection(String username) {
        connection = new ClientConnectionImpl(username);
    }
}
