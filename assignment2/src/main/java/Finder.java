/*
    Tripp Isbell
    cai0004@auburn.edu
    COMP3700 Assignment 2
    Github and JUnit practice

    Finder is a class with two simple methods for finding the max and min values in an array.
    So that null can be returned for empty/null inputs, Integer wrappers are used instead of primitives.
 */

public class Finder {
    /*
        Finds and returns max value of intArray
        Uses Integer wrapper to return null if input array is empty or invalid
     */
    public static Integer findMax(Integer[] intArray) {
        if (intArray == null || intArray.length == 0) {
            return null;
        }
        return recurseMax(intArray, 0);
    }
    /*
        Finds and returns min value of intArray
        Uses Integer wrapper to return null if input array is empty or invalid

        Also it first negates the sign of the entire array and then finds the max
        recursively so its pretty suboptimal
     */
    public static Integer findMin(Integer[] intArray) {
        if (intArray == null || intArray.length == 0) {
            return null;
        }
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = -intArray[i];
        }
        return -recurseMax(intArray, 0);
    }

    /*
        private recurse method that is really just an iterative scan done recursively
     */
    private static Integer recurseMax(Integer[] intArray, int p) {
        // Since empty case is already covered in wrapper method
        if (p == intArray.length - 1) {
            return intArray[p];
        }
        else {
            return Math.max(intArray[p], recurseMax(intArray, p+1));
        }
    }
}
