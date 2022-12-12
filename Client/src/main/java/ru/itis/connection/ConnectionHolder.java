package ru.itis.connection;

import ru.itis.utils.ErrorHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionHolder {
    private static Client connection;

    public static Client getConnection() {
        if (connection == null) {
            try {
                connection = new Client(InetAddress.getLocalHost(), 8088);
            } catch (UnknownHostException ex) {
                new ErrorHandler().handleError("Invalid host");
            }
        }
        return connection;
    }
}
