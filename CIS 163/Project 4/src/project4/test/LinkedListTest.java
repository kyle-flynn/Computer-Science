package project4.test;

import project4.SimpleLinkedList;

/**
 * Created by Kyle Flynn on 11/24/2016.
 */
public class LinkedListTest {

    public static void main(String[] args) {

        SimpleLinkedList<String> names = new SimpleLinkedList<>();

        names.add("Kyle");
        names.add("Joe");
        names.add("Connor");

        System.out.println(names.toString());
        names.remove("Joe");
        System.out.println(names.toString());
        names.push("Nathan");
        System.out.println(names.toString());
        names.pop();
        System.out.println(names.toString());
    }

}
