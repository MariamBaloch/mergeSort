import java.util.Arrays;

public class MergeSort {

    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) return array; // Base case: already sorted

        int mid = array.length / 2;

        int[] left  = mergeSort(Arrays.copyOfRange(array, 0,   mid)); // Sort left half 0-mid
        int[] right = mergeSort(Arrays.copyOfRange(array, mid, array.length)); // Sort right half mid-end

        return merge(left, right);
    }

    // ── Merge two sorted halves into one sorted array ──────────────────────────
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        int leftIndex   = 0;
        int rightIndex  = 0;
        int resultIndex = 0;

        // Pick the smaller element from each half, one at a time
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }

        // Pour in whatever is left over
        while (leftIndex  < left.length)  result[resultIndex++] = left[leftIndex++];
        while (rightIndex < right.length) result[resultIndex++] = right[rightIndex++];

        return result;
    }

    // ── Demo ───────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        int[] numbers = {40,10,60,5,30,15,50,20};

        System.out.println("Before: " + Arrays.toString(numbers));
        int[] sorted = mergeSort(numbers);
        System.out.println("After:  " + Arrays.toString(sorted));
    }
}