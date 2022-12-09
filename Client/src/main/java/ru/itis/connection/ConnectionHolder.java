package ru.itis.connection;

import ru.itis.utils.ErrorHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionHolder {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = new PlayerConnection(InetAddress.getLocalHost(), 8088);
            } catch (UnknownHostException ex) {
                new ErrorHandler().handleError("Invalid host");
            }
        }
        return connection;
    }
}
