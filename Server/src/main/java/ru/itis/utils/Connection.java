package ru.itis.utils;

import ru.itis.messages.Message;

import java.io.IOException;

public interface Connection {

    void send(Message message) throws IOException;

    Information getInformation();

    void close();

    int getId();

    boolean isConnected();
}
