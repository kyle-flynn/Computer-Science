package project4;

/*****************************************************************
 Exception class that is fired whenever our LinkedList is called
 out of bounds such that it's size is exceeded or is indexed at
 less than zero.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class LinkedListOutOfBoundsException extends Exception {

    /*****************************************************************
     Constructor that displays the desired message.
     *****************************************************************/
    public LinkedListOutOfBoundsException(String message) {
        super(message);
    }

    /*****************************************************************
     Default constructor that sends a message that the List index is
     out of bounds.
     *****************************************************************/
    public LinkedListOutOfBoundsException() {
        super("Trying to access data outside of the list size.");
    }

}
