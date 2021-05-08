package com.codes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CodingRounds {

    public static int minMeetingRooms(int[][] intervals) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] i : intervals) {
            if (map.containsKey(i[0])) {
                map.put(i[0], map.get(i[0]) + 1);
            } else map.put(i[0], 1);

            if (map.containsKey(i[1])) {
                map.put(i[1], map.get(i[1]) - 1);
            } else map.put(i[1], -1);
        }
        int[] pre = map.values().stream().mapToInt(Integer::intValue).toArray();
        int max = pre[0];
        for (int i = 1; i < pre.length; i++) {
            pre[i] += pre[i - 1];
            max = Math.max(max, pre[i]);
        }
        return max;
    }


    public static long playlist(Integer[] a) {
        long[] song = new long[60];
        for (int i = 0; i < a.length; i++) {
            ++song[a[i] % 60];
        }
        long sum = song[0] / 2;
        for (int i = 1; i < 30; i++) {
            sum += Math.min(song[i], song[60 - i]);
        }
        sum += song[30] / 2;
        return sum;
    }

    static int upSpeedDownSpeed(int desired, int up, int down) {
        int height = 0;
        int days = 0;
        do {
            height += up;
            days++;
            if (height >= desired) break;
            height -= down;
        } while (true);
        return days;
    }

    static List<String> getDailPadCombination(String s) {

        List<String> strings = new ArrayList<>();
        if (s.length() == 0) return strings;
        HashMap<String, List<String>> stringListHashMap = new HashMap<>();
        stringListHashMap.put("2", Arrays.asList("a", "b", "c"));
        stringListHashMap.put("3", Arrays.asList("def".split("")));
        stringListHashMap.put("4", Arrays.asList("ghi".split("")));
        stringListHashMap.put("5", Arrays.asList("jkl".split("")));
        stringListHashMap.put("6", Arrays.asList("mno".split("")));
        stringListHashMap.put("7", Arrays.asList("pqrs".split("")));
        stringListHashMap.put("8", Arrays.asList("tuv".split("")));
        stringListHashMap.put("9", Arrays.asList("wxyz".split("")));
        strings.addAll(stringListHashMap.get(s.substring(0, 1)));
        for (int i = 1; i < s.length(); i++) {
            final List<String> temp = stringListHashMap.get(s.substring(i, i + 1));
            strings = strings.stream().flatMap(s1 -> temp.stream().map(s2 -> s1 + s2)).collect(Collectors.toList());
        }
        return strings;
    }

    static String stringReversalExceptInteger(String s) {
        List<String> ar = new ArrayList<>();
        ar.addAll(Arrays.asList(s.split(" ")));
        Collections.reverse(ar);
        ar = ar.stream().map(s1 -> {
            if (!s1.matches("[0-9]+"))
                return new StringBuffer(s1).reverse().toString();
            else return s1;
        }).collect(Collectors.toList());

        return ar.stream().collect(Collectors.joining(" "));
    }

    static String[] sortToArray(String source) {
        return Arrays.stream(source.split(" ")).map(s ->
                Arrays.stream(s.split("")).sorted().collect(Collectors.joining())
        ).sorted().toArray(String[]::new);
    }

    static boolean nestedAnagram(String source, String target) {
        if (source.length() != target.length()) {
            return false;
        }
        String[] sourceArray = sortToArray(source);
        String[] targetArray = sortToArray(target);
        for (int i = 0; i < sourceArray.length; i++) {
            if (!sourceArray[i].equals(targetArray[i])) return false;
        }
        return true;
    }

    static int[] stringToArray(String arr) {
        int[] array = new int[26];
        for (char c : arr.toCharArray()) {
            array[c - 'a'] += 1;
        }
        return array;
    }

    static int possibleTransfromations(String initial, String[] dict) {
        int count = 0;
        int[] start = stringToArray(initial);
        for (String i : dict) {
            int[] temp = stringToArray(i);
            int piv = 0, changeCount = 0;
            boolean flag = true;
            for (int j = 0; j < 26; j++) {
                if (start[j] != temp[j]) {
                    if (changeCount < 2) {

                        changeCount++;
                    } else {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag && piv == 0) {
                count++;
            }
        }
        return count;
    }


}