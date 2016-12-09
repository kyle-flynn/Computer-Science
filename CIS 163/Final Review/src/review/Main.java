package review;

/**
 * Created by Kyle Flynn on 12/6/2016.
 */
public class Main {

    public static void main(String[] args) {
        BinarySearch bSearch = new BinarySearch();
        int[] sortedArray = {1, 4, 8, 16, 26, 40, 60};

        bSearch.find(40, sortedArray);
    }

}
