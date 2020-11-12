package com.sumitbmdi.linux_path_traversal.exceptions;

/**
 * Created by sumit on 21/01/17.
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
