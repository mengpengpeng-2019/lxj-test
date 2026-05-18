package com.lxj.algorithm;

/**
 * Binary search implementation for sorted integer arrays.
 */
public final class BinarySearch {
    private BinarySearch() {
    }

    /**
     * Returns the index of {@code target} in {@code sortedValues}, or {@code -1}
     * when the target is not present.
     *
     * @param sortedValues values sorted in ascending order
     * @param target       value to find
     * @return index of the target, or -1 when absent
     */
    public static int indexOf(int[] sortedValues, int target) {
        if (sortedValues == null) {
            throw new IllegalArgumentException("sortedValues must not be null");
        }

        int low = 0;
        int high = sortedValues.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            int current = sortedValues[middle];

            if (current == target) {
                return middle;
            }

            if (current < target) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }

        return -1;
    }
}
