package project4;

/**
 * Created by Kyle Flynn on 11/24/2016.
 */
public class LinkedListOutOfBoundsException extends Exception {

    public LinkedListOutOfBoundsException(String message) {
        super(message);
    }

    public LinkedListOutOfBoundsException() {
        super("Trying to access data outside of the list size.");
    }

}
