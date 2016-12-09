package review.LinkedList;

/**
 * Created by Kyle Flynn on 12/8/2016.
 */
public class SinglyLinkedList<E> {

    private int size;
    private SingleNode<E> list;

    public SinglyLinkedList() {
        this.size = 0;
        this.list = null;
    }

    public void add(E value) {
        if (size == 0 || list == null) {
            list = new SingleNode<E>(value);
        } else {
            SingleNode<E> current = list;
            for(int i = 0; i < size - 1; i++) {
                current = list.getNext();
            }
            current.setNext(new SingleNode<E>(value));
        }
        size++;
    }

    public void insert(int pos, E value) {

        if (pos > size || pos < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (size == pos) {
            add(value);
        } else {
            SingleNode<E> current = list;
            for(int i = 0; i < pos - 1; i++) {
                current = list.getNext();
            }
            current.setNext(new SingleNode<E>(value, current.getNext()));
            size++;
        }

    }

    public E remove(int pos) {
        if (pos > size || pos < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (pos == 0) {
            size--;
            list = list.getNext();
            return list.getValue();
        }

        SingleNode<E> current = list;

        try {
            for (int i = 0; i < pos - 1; i++) {
                current = current.getNext();
            }
        } catch(Exception e) {
            return null;
        }

        SingleNode<E> removedNode = current.getNext();
        current.setNext(current.getNext().getNext());
        size--;

        return removedNode.getValue();
    }

    @Override
    public String toString() {
        SingleNode<E> current = list;
        String data = "";

        while (current != null) {
            data+= current.getValue() + ", ";
            current = current.getNext();
        }
        return data;
    }

}
