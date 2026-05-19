package com.lxj.algorithm;

/**
 * Self-contained test cases for {@link BinarySearch}.
 */
public final class BinarySearchTest {
    private BinarySearchTest() {
    }

    public static void main(String[] args) {
        findsTargetInMiddle();
        findsTargetsAtArrayBoundaries();
        returnsMinusOneWhenTargetIsAbsent();
        handlesEmptyArray();
        rejectsNullArray();
        findsMaxInRotatedSortedArray();
        findsMaxInUnrotatedSortedArray();
        rejectsEmptyArrayWhenFindingMax();

        System.out.println("All BinarySearch tests passed.");
    }

    private static void findsTargetInMiddle() {
        int[] values = {-9, -3, 0, 4, 7, 11, 18};

        assertEquals(3, BinarySearch.indexOf(values, 4), "target in middle");
    }

    private static void findsTargetsAtArrayBoundaries() {
        int[] values = {2, 5, 8, 13, 21};

        assertEquals(0, BinarySearch.indexOf(values, 2), "target at first index");
        assertEquals(4, BinarySearch.indexOf(values, 21), "target at last index");
    }

    private static void returnsMinusOneWhenTargetIsAbsent() {
        int[] values = {1, 3, 5, 7, 9};

        assertEquals(-1, BinarySearch.indexOf(values, 4), "missing target between values");
        assertEquals(-1, BinarySearch.indexOf(values, 0), "missing target before first value");
        assertEquals(-1, BinarySearch.indexOf(values, 10), "missing target after last value");
    }

    private static void handlesEmptyArray() {
        assertEquals(-1, BinarySearch.indexOf(new int[0], 6), "empty array");
    }

    private static void rejectsNullArray() {
        try {
            BinarySearch.indexOf(null, 1);
        } catch (IllegalArgumentException expected) {
            return;
        }

        throw new AssertionError("Expected IllegalArgumentException for null array");
    }

    private static void findsMaxInRotatedSortedArray() {
        int[] values = {13, 21, 34, 2, 5, 8};

        assertEquals(34, BinarySearch.maxOfRotatedSortedArray(values), "max in rotated array");
    }

    private static void findsMaxInUnrotatedSortedArray() {
        int[] values = {1, 3, 5, 7, 9};

        assertEquals(9, BinarySearch.maxOfRotatedSortedArray(values), "max in unrotated array");
        assertEquals(6, BinarySearch.maxOfRotatedSortedArray(new int[] {6}), "single value array");
    }

    private static void rejectsEmptyArrayWhenFindingMax() {
        try {
            BinarySearch.maxOfRotatedSortedArray(new int[0]);
        } catch (IllegalArgumentException expected) {
            return;
        }

        throw new AssertionError("Expected IllegalArgumentException for empty array");
    }

    private static void assertEquals(int expected, int actual, String scenario) {
        if (expected != actual) {
            throw new AssertionError(
                    scenario + ": expected " + expected + " but was " + actual);
        }
    }
}
