package game1024;

/**
 * Created by kylef_000 on 10/8/2016.
 */
public class FactorialTest {

    public static int factorial(int num) {
        if (num < 1) {
            throw new IndexOutOfBoundsException("Integer must be greater than or equal to 1. Entered: " + num);
        }
        return num == 1 ? 1 : num * factorial(num-1);
    }

    public static void main(String[] args) {
        System.out.println(factorial(3));
        System.out.println(factorial(4));
        System.out.println(factorial(5));
        System.out.println(factorial(0));
    }

}
