package review;

/**
 * Created by Kyle Flynn on 12/8/2016.
 */
public class VowelCounter {

    private char[] vowels;

    public VowelCounter() {
        vowels = new char[]{'a', 'e', 'i', 'o', 'u'};
    }

    public int count(String word) {
        if (word.length() == 0) {
            return 0;
        } else {
            for (char vowel : vowels) {
                if (word.charAt(0) == vowel) {
                    return 1 + count(word.substring(1));
                }
            }
            return count(word.substring(1));
        }
    }

    public static void main(String[] args) {
        VowelCounter vowelCounter = new VowelCounter();
        System.out.println(vowelCounter.count("HelloWorld!"));
    }

}
