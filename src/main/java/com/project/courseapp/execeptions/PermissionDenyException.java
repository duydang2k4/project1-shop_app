package com.project.courseapp.execeptions;

public class PermissionDenyException extends Exception{
    public PermissionDenyException(String message) {
        super(message);
    }
}
