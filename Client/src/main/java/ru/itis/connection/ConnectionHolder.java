package ru.itis.connection;

public interface ConnectionHolder {
    void closeConnection() throws Exception;

//    boolean isConnectionInitialized();

    void initializeConnection(String username);

}
