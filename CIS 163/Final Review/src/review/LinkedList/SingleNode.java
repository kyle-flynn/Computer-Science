package review.LinkedList;

/**
 * Created by Kyle Flynn on 12/8/2016.
 */
public class SingleNode<E> {

    private E value;
    private SingleNode<E> nextNode;

    public SingleNode(E value) {
        this.value = value;
        this.nextNode = null;
    }

    public SingleNode(E value, SingleNode<E> next) {
        this.value = value;
        this.nextNode = next;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setNext(SingleNode<E> next) {
        this.nextNode = next;
    }

    public E getValue() {
        return value;
    }

    public SingleNode<E> getNext() {
        return nextNode;
    }

}
