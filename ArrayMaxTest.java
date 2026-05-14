public class ArrayMaxTest {
    public static int findMax(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] numbers = {3, 8, 2, 15, 6, 9};
        int max = findMax(numbers);
        System.out.println("数组中的最大数是: " + max);
    }
}
