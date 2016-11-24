package project4;

import java.io.Serializable;

/**
 * Created by Kyle Flynn on 11/23/2016.
 */
public class SimpleLinkedList<GenericObject> implements Serializable {

    private SimpleNode<GenericObject> head;
    private SimpleNode<GenericObject> tail;
    private int size;

    public SimpleLinkedList() {
        head = tail = null;
        size = 0;
    }

    public SimpleNode<GenericObject> add(GenericObject obj) {

        /** This is our new current head of the linked list */
        SimpleNode<GenericObject> newHead = new SimpleNode<>(obj);

        // If the list is empty, then the first/last element is the node
        if (head == null || tail == null) {
            head = tail = newHead;
        } else {
            // Our previous head must now link forward to the new head
            head.setNextNode(newHead);

            // Our class head element must now be equal to the new head we created
            head = newHead;
        }

        // Increment the size of the list
        size++;

        return newHead;
    }

    public SimpleNode<GenericObject> remove(GenericObject obj) {

        /** The node that is removed, it may be null */
        SimpleNode<GenericObject> removedNode = null;

        // First, we check the first node in the list
        if (tail.getValue().equals(obj)) {

            removedNode = tail;

            // If our list size is greater than 1, then re-assign the tail
            if (tail.getNextNode() != null) {
                tail = tail.getNextNode();
            } else {

                // If our list is exactly one item, now it is empty
                tail = null;
            }

            return removedNode;

        } else {

            /* In order to remove an obj at an index, we must traverse through the entire
               list to see whether ot not there is a match */
            SimpleNode<GenericObject> currentObj = tail;

            // Now we iterate through each node's next pointer
            while (currentObj.getNextNode() != null) {

                // Now we check if the value of the current node equals the object
                if (currentObj.getNextNode().getValue().equals(obj)) {

                    // Finally, delete that node, and re-align the other nodes
                    removedNode = currentObj.getNextNode();
                    currentObj.setNextNode(removedNode.getNextNode());

                    return removedNode;
                }

                currentObj = currentObj.getNextNode();
            }

        }

        // Decrease the list size
        size--;

        // Return null if element was not removed, otherwise return the removed node
        return removedNode;
    }

    public SimpleNode<GenericObject> get(GenericObject obj) {
        // First, we check the first node in the list
        if (tail.getValue().equals(obj)) {
            return tail;
        } else {

            /* In order to remove an obj at an index, we must traverse through the entire
               list to see whether ot not there is a match */
            SimpleNode<GenericObject> currentObj = tail;

            // Now we iterate through each node's next pointer
            while (currentObj.getNextNode() != null) {

                // Now we check if the value of the current node equals the object
                if (currentObj.getNextNode().getValue().equals(obj)) {
                    return currentObj.getNextNode();
                }

                currentObj = currentObj.getNextNode();
            }

        }

        // Return null if element was not removed, otherwise return the removed node
        return null;
    }

    public SimpleNode<GenericObject> push(GenericObject obj) {

        /** Reference to the new node that is created */
        SimpleNode<GenericObject> newTail = new SimpleNode<>(obj);

        /** Reference to the old tail */
        SimpleNode<GenericObject> oldTail = tail;

        // Assign a new tail, and assign it's next node to the old tail
        tail = newTail;
        tail.setNextNode(oldTail);

        size++;

        return tail;
    }

    public SimpleNode<GenericObject> pop() {

        /** Reference to the old head that was just popped off */
        SimpleNode<GenericObject> oldHead = head;

        // First, we check the first node in the list
        if (tail == head) {

            // If we're popping off the last item, reset the list.
            clear();
        } else {

            /* In order to pop an obj off the list, we must traverse through the entire
               list, and then assign the node before the head, as the head. */
            SimpleNode<GenericObject> currentObj = tail;

            // Now we iterate through each node's next pointer
            while (currentObj.getNextNode() != null) {

                // Now we check if the next node equals the head
                if (currentObj.getNextNode() == head) {
                    head = currentObj;
                    head.setNextNode(null);

                    // It is crucial that we return here, so that the loop stops
                    return oldHead;
                }

                currentObj = currentObj.getNextNode();
            }

        }

        size--;

        return oldHead;
    }

    @Override
    public String toString() {

        /** The current node that we are looping through */
        SimpleNode<GenericObject> currentObj = tail;

        /** Displays the value of the nodes as a String */
        String items = "";

        while (currentObj != null) {

            /* To make the list look nice, we control when to add a node separator
               We use the toString() method in case the node's value isn't already a String/int */
            if (currentObj.getNextNode() == null) {
                items += currentObj.getValue().toString() + "";
            } else {
                items += currentObj.getValue().toString() + ",";
            }

            // Move through the list
            currentObj = currentObj.getNextNode();
        }

        return items;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

}
