package com.drny.forum;

public class Test {

    public static void main(String[] args) {
        int[] array = {12, 2, 100, 212, 120};
        int max = max(array);
        System.out.println("max = " + max);
    }

    public static int max(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

}
