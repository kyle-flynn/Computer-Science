package review.LinkedList;

/**
 * Created by Kyle Flynn on 12/8/2016.
 */
public class TestLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList<String> names = new SinglyLinkedList<>();

        names.add("Kyle");
        names.add("Joe");
        names.add("Connor");
        names.insert(1, "Evan");

        System.out.println(names.toString());
        System.out.println(names.remove(1));
        System.out.println(names.toString());
        System.out.println(names.remove(2));
        System.out.println(names.toString());
    }

}
