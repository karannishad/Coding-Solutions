package com.codes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Nock {
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] lis = new int[n];
        for (int i = 0; i < n; i++) {
            lis[i] = 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (nums[j] > nums[i]) {
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                }
            }
        }
        int result = lis[0];
        for (int i = 1; i < n; i++) {
            result = Math.max(lis[i], result);
        }
        return result;
    }

    public static int twoDimensional(List<String> cor) {

        int[][] coord = new int[cor.size()][2];
        int i = 0;
        for (String s : cor) {
            String[] strings = s.split(" ");
            coord[i][0] = Integer.parseInt(strings[0]);
            coord[i][1] = Integer.parseInt(strings[1]);
            i++;
        }

        int arrlen = Arrays.stream(coord).map(x -> Arrays.stream(x).max().getAsInt()).max(Integer::compareTo).get();
        int[][] revar = new int[arrlen][arrlen];
        for (int[] a : coord) {
            for (int j = 0; j <= a[0] - 1; j++) {
                for (int k = 0; k <= a[1] - 1; k++) {
                    revar[j][k]++;
                }
            }
        }
        return getMaxof2dArray(revar, cor.size());
    }

    static int getMaxof2dArray(int[][] a, int ele) {
        int count = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] == ele) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws InterruptedException, IOException {

    }
}
