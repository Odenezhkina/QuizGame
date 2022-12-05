package ru.itis.messages;

import ru.itis.constants.MessageTypes;

import java.io.Serializable;

public interface Message<T> {
    MessageTypes getType();

    T getContent();

    int getSenderId();
}
