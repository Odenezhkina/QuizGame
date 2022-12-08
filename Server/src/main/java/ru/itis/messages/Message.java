package ru.itis.messages;

import ru.itis.constants.MessageTypes;

import java.io.Serializable;

public abstract class Message<T> {
  public abstract MessageTypes getType();

  public abstract T getContent();

  public abstract int getSenderId();
}
