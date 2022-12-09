package ru.itis.protocol.message;

import ru.itis.constants.MessageTypes;

public abstract class Message<T> {
  public abstract MessageTypes getType();

  public abstract T getContent();

  public abstract int getSenderId();
}
