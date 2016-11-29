package project4;

import java.io.Serializable;

/*****************************************************************
 Simple Double Node class. Key element to a doubly linked list.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class SimpleNode<GenericValue> implements Serializable {

    /** A generic that holds the value of the node. */
    private GenericValue value;

    /** Reference of this class that holds the next node. */
    private SimpleNode<GenericValue> nextNode;

    /** Reference of this class that holds the previous node. */
    private SimpleNode<GenericValue> prevNode;

    /*****************************************************************
     Default constructor that initializes the declared variables.
     @param value The value of the node.
     *****************************************************************/
    public SimpleNode(GenericValue value) {
        this.value = value;
        this.nextNode = null;
        this.prevNode = null;
    }

    /*****************************************************************
     Method that sets the value of the node.
     @param value The value of the node.
     *****************************************************************/
    public void setValue(GenericValue value) {
        this.value = value;
    }

    /*****************************************************************
     Method that sets the reference to the next node.
     @param nextValue The reference to the next node.
     *****************************************************************/
    public void setNextNode(SimpleNode nextValue) {
        this.nextNode = nextValue;
    }

    /*****************************************************************
     Method that sets the reference to the previous node.
     @param prevValue The reference to the previous node.
     *****************************************************************/
    public void setPrevNode(SimpleNode prevValue) {
        this.prevNode = prevValue;
    }

    /*****************************************************************
     Getter that returns the value of this node.
     @return value as a generic.
     *****************************************************************/
    public GenericValue getValue() {
        return value;
    }

    /*****************************************************************
     Getter that returns the next node reference.
     @return The next node as a SimpleNode.
     *****************************************************************/
    public SimpleNode<GenericValue> getNextNode() {
        return nextNode;
    }


    /*****************************************************************
     Getter that returns the previous node reference.
     @return The previous node as a SimpleNode.
     *****************************************************************/
    public SimpleNode<GenericValue> getPrevNode() {
        return prevNode;
    }

}
