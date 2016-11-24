package project4;

import java.io.Serializable;

/**
 * Created by Kyle Flynn on 11/23/2016.
 */
public class SimpleNode<GenericValue> implements Serializable {

    private GenericValue value;
    private SimpleNode nextNode;

    public SimpleNode(GenericValue value) {
        this.value = value;
        this.nextNode = null;
    }

    public void setValue(GenericValue value) {
        this.value = value;
    }

    public void setNextNode(SimpleNode nextValue) {
        this.nextNode = nextValue;
    }

    public GenericValue getValue() {
        return value;
    }

    public SimpleNode getNextNode() {
        return nextNode;
    }

}
