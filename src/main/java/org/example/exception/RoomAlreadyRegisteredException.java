package org.example.exception;

public class RoomAlreadyRegisteredException extends Exception {
    public RoomAlreadyRegisteredException(String message) {
        super(message);
    }
}
