package project4;

import java.io.Serializable;

/**
 * Created by Kyle Flynn on 11/23/2016.
 */
public class SimpleNode<GenericValue> implements Serializable {

    private GenericValue value;
    private SimpleNode<GenericValue> nextNode;
    private SimpleNode<GenericValue> prevNode;

    public SimpleNode(GenericValue value) {
        this.value = value;
        this.nextNode = null;
        this.prevNode = null;
    }

    public void setValue(GenericValue value) {
        this.value = value;
    }

    public void setNextNode(SimpleNode nextValue) {
        this.nextNode = nextValue;
    }

    public void setPrevNode(SimpleNode prevValue) {
        this.prevNode = prevNode;
    }

    public GenericValue getValue() {
        return value;
    }

    public SimpleNode<GenericValue> getNextNode() {
        return nextNode;
    }

    public SimpleNode<GenericValue> getPrevNode() {
        return prevNode;
    }

}
