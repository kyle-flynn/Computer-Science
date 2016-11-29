package project4;

/*****************************************************************
 Exception class that is fired whenever our LinkedList is empty
 and an operation such as remove() or get() is called.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class LinkedListEmptyException extends Exception {

    /*****************************************************************
     Constructor that sends a desired message that the list is empty.
     *****************************************************************/
    public LinkedListEmptyException(String message) {
        super(message);
    }

    /*****************************************************************
     Default constructor that sends a message that the List is empty.
     *****************************************************************/
    public LinkedListEmptyException() {
        super("The list is empty. Add data to the list.");
    }

}
