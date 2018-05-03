package cz.muni.fi.pb138.ODSVideo.exceptions;

public class IllegalEntityException extends Exception{
    public IllegalEntityException() {
        super();
    }

    public IllegalEntityException(String message) {
        super(message);
    }

    public IllegalEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
