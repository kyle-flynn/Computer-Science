package project4;

/**
 * Created by Kyle Flynn on 11/24/2016.
 */
public class LinkedListEmptyException extends Exception {

    public LinkedListEmptyException(String message) {
        super(message);
    }

    public LinkedListEmptyException() {
        super("The list is empty. Add data to the list.");
    }

}
