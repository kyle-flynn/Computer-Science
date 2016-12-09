package review;

/**
 * Created by Kyle Flynn on 12/6/2016.
 */
public class BinarySearch implements Search {

    @Override
    public boolean find(int goal, int[] array) {
        int max = array.length - 1;
        int min = 0;

        int iterations = 0;

        while(max >= min) {
            iterations++;
            int mid = (max + min) / 2;

            if (array[mid] == goal) {
                print("Found element at index " + mid);
                print("Iterations: " + iterations);
                return true;
            }

            if (array[mid] < goal) {
                min = mid + 1;
            }

            if (array[mid] > goal) {
                max = mid - 1;
            }

        }

        print("Iterations: " + iterations);
        return false;
    }

    private void print(Object obg) {
        System.out.println("MSG: " + obg);
    }

}
