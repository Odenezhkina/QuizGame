package ru.itis.connection.impl;

import ru.itis.connection.ConnectionHolder;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;

public class ConnectionHolderImpl implements ConnectionHolder {
    private static ClientConnectionImpl connection;
    private static String playerUsername;

    public static ClientConnectionImpl getConnection() throws ConnectionNotInitializedException {
        if (connection == null) {
            throw new ConnectionNotInitializedException("Unknown connection");
        }
        return connection;
    }

    @Override
    public void closeConnection() throws Exception {
        connection.close();
    }

//    @Override
//    public boolean isConnectionInitialized() {
//        return playerUsername == null;
//    }

    @Override
    public void initializeConnection(String username) {
        connection = new ClientConnectionImpl(username);
    }
}
