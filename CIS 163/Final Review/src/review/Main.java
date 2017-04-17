package review;

/**
 * Created by Kyle Flynn on 12/6/2016.
 */
public class Main {

    public static void main(String[] args) {
        BinarySearch bSearch = new BinarySearch();
        int[] sortedArray = {1, 4, 8, 16, 26, 40, 60};

        bSearch.find(40, sortedArray);

        QuickSort qSort = new QuickSort();
        int[] unsortedArray = new int[]{4, 6, 1, 7, 1, 78, 2, 6, 10, 5, 12, 2, 26};

        qSort.sort(unsortedArray, 0, unsortedArray.length - 1);
    }

}
