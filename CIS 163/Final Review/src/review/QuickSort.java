package review;

/**
 * Created by Kyle Flynn on 12/6/2016.
 */
public class QuickSort implements Sort {

    @Override
    public void sort(int[] array, int start, int end) {

        int i = start;
        int j = end;
        int pivot = array[start];

        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        if (start < j) {
            sort(array, start, j);
        }
        if (i < end) {
            sort(array, i, end);
        }

        String a = "";
        for (int n : array) {
            a+= n + ",";
        }
        System.out.println(a);
    }

}
