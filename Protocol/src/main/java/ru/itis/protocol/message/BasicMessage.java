package ru.itis.protocol.message;

import ru.itis.constants.MessageTypes;

public interface BasicMessage {
    MessageTypes getType();

    int getSenderId();
}
