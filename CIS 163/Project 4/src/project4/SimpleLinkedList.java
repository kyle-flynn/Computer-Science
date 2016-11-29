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

    public GenericObject add(GenericObject obj) {

        /** This is our new current head of the linked list */
        SimpleNode<GenericObject> newHead = new SimpleNode<>(obj);

        // If the list is empty, then the first/last element is the node
        if (head == null || tail == null) {
            head = tail = newHead;
        } else {

            // Our previous head must now link forward to the new head
            head.setNextNode(newHead);

            // Our new node must now link to the new previous node
            newHead.setPrevNode(head);

            // Our class head element must now be equal to the new head we created
            head = newHead;
        }

        // Increment the size of the list
        size++;

        return newHead.getValue();
    }

    public GenericObject remove(GenericObject obj) {

        /** The node that is removed, it may be null */
        SimpleNode<GenericObject> removedNode = null;

        /** In order to remove an obj, we must traverse through the entire
         list to see whether ot not there is a match */
        SimpleNode<GenericObject> currentObj = tail;

        // Now we iterate through each node's next pointer
        while (currentObj != null) {

            // Now we check if the value of the current node equals the object
            if (currentObj.getValue().equals(obj)) {

                // Finally, delete that node, and re-align the other nodes
                removedNode = currentObj;

                if (head == tail) {
                    clear();
                    return currentObj.getValue();
                }

                // The only way the previous node can be null is if we are working with the tail
                if (removedNode.getPrevNode() == null) {
                    currentObj.getNextNode().setPrevNode(null);
                    tail = currentObj.getNextNode();
                } else {
                    currentObj.getPrevNode().setNextNode(removedNode.getNextNode());
                }

                // The only way the next node can be null is if we are working with the tail
                if (removedNode.getNextNode() == null) {
                    currentObj.getPrevNode().setNextNode(null);
                    head = currentObj.getPrevNode();
                } else {
                    currentObj.getNextNode().setPrevNode(removedNode.getPrevNode());
                }

                // Decrease the list size
                size--;

                return removedNode.getValue();
            }

            // Traverse through the list by going to the next node
            currentObj = currentObj.getNextNode();
        }

        return null;
    }

    public GenericObject remove(int index) {

        if (index > size || index < 0) {
            // Throw exception!
            return null;
        } else {

            /** The node that is removed, it may be null */
            SimpleNode<GenericObject> removedNode = null;

            /** In order to remove an obj at an index, we must traverse through the entire
             list to see whether ot not there is a match */
            SimpleNode<GenericObject> currentObj = tail;

            while (currentObj != null) {

                if (index == 0) {

                    // Finally, delete that node, and re-align the other nodes
                    removedNode = currentObj;

                    if (head == tail) {
                        clear();
                        return currentObj.getValue();
                    }

                    // The only way the previous node can be null is if we are working with the tail
                    if (removedNode.getPrevNode() == null) {
                        currentObj.getNextNode().setPrevNode(null);
                        tail = currentObj.getNextNode();
                    } else {
                        currentObj.getPrevNode().setNextNode(removedNode.getNextNode());
                    }

                    // The only way the next node can be null is if we are working with the tail
                    if (removedNode.getNextNode() == null) {
                        currentObj.getPrevNode().setNextNode(null);
                        head = currentObj.getPrevNode();
                    } else {
                        currentObj.getNextNode().setPrevNode(removedNode.getPrevNode());
                    }

                    // Decrease the list size
                    size--;

                    return removedNode.getValue();
                } else {
                    currentObj = currentObj.getNextNode();
                    index--;
                }
            }

        }

        return null;

    }

    public GenericObject get(GenericObject obj) {

        /** In order to get an obj, we must traverse through the entire
         list to see whether ot not there is a match */
        SimpleNode<GenericObject> currentObj = tail;

        while (currentObj != null) {

            // Now we check if the value of the current node equals the object
            if (currentObj.getValue().equals(obj)) {
                return currentObj.getValue();
            }

            // Now traverse by going to the next node
            currentObj = currentObj.getNextNode();
        }

        // Return null if element was not removed, otherwise return the removed node
        return null;
    }

    public GenericObject get(int index) {
        if (index > size) {
            return null;
        } else {

            /** In order to get an obj at an index, we must traverse through the entire
             list to see whether ot not there is a match */
            SimpleNode<GenericObject> currentObj = tail;

            while (currentObj != null) {

                if (index == 0) {
                    return currentObj.getValue();
                } else {
                    currentObj = currentObj.getNextNode();
                    index--;
                }
            }

        }

        return null;
    }

    public GenericObject push(GenericObject obj) {

        /** Reference to the new node that is created */
        SimpleNode<GenericObject> newTail = new SimpleNode<>(obj);

        /** Reference to the old tail */
        SimpleNode<GenericObject> oldTail = tail;

        // Assign it's next node to the old tail
        newTail.setNextNode(oldTail);

        oldTail.setPrevNode(newTail);

        // Our new tail is the new tail
        tail = newTail;

        size++;

        return tail.getValue();
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

    public GenericObject pop() {

        /** Reference to the old head that was just popped off */
        SimpleNode<GenericObject> oldHead = head;

        // First, we check the first node in the list
        if (tail == head) {

            // If we're popping off the last item, reset the list.
            clear();
        } else {

            /** The new head is the previous node of the old head */
            SimpleNode<GenericObject> newHead = head.getPrevNode();

            // The next node is now null
            newHead.setNextNode(null);

            // And we have our new head!
            head = newHead;

            // Decrease the list size
            size--;

        }

        return oldHead.getValue();
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

}
