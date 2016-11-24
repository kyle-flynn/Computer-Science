package project4;

/**
 * Created with IntelliJ IDEA.
 * User: fergusor
 * Date: 3/14/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;

public class MyLinkedList<E> implements Serializable
{
    private DNode<E> top;
    public int size;

    public MyLinkedList() {
        top = null;
        size = 0;
    }

   
}
