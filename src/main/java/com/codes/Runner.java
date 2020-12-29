package com.codes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Runner {
    final private static Logger LOG = LoggerFactory.getLogger(Runner.class);

    static int getPowerandValue(int i) {
        int count = 0;
        if ((i & (i - 1)) == 0) return i;
        while (i != 0) {
            count++;
            i /= 2;
        }
        return 1 << count;
    }

    static int sortLexigo(int x, int y) {
        String sx = Integer.toString(x);
        String sy = Integer.toString(y);
        if (sx.length() == sy.length()) {
            if (sx.charAt(0) == sy.charAt(0)) {
                return sortLexigo(Integer.parseInt(sx.substring(1)), Integer.parseInt(sy.substring(1)));
            } else if (sx.charAt(0) > sy.charAt(0)) {
                return -1;
            } else return 1;
        } else if (sx.length() > sy.length())
            return 1;
        else return -1;
    }

    static String printMax(List<Integer> a) {
        Map<String, ArrayList<Integer>> ma = new TreeMap<>(Collections.reverseOrder());

        for (int i : a) {
            String key = Integer.toString(i).substring(0, 1);
            if (ma.containsKey(key)) {
                ma.get(key).add(i);
            } else {
                ma.put(key, new ArrayList<>(Arrays.asList(i)));
            }
        }


        StringBuilder sb = new StringBuilder();
        for (List<Integer> s : ma.values()) {
            sb.append(s.stream().sorted(Runner::sortLexigo).map(Objects::toString).collect(Collectors.joining("")));
        }

        return sb.toString();
    }

    public static int minimumProcessingTime(List<Integer> processStartTime, List<Integer> predicatedTaskTime) {

        int[][] arr = new int[processStartTime.size()][4];
        int max = 0;
        Collections.sort(predicatedTaskTime);
        Iterator<Integer> iterator = predicatedTaskTime.iterator();
        for (int[] i : arr) {
            for (int j = 0; j < 4; j++) {
                i[j] = iterator.next();
            }
        }
        Collections.sort(processStartTime, Collections.reverseOrder());
        iterator = processStartTime.iterator();
        for (int[] i : arr) {
            max = Math.max(iterator.next() + i[3], max);
        }
        return max;
    }

    public static int SeatingStudents(int[] arr) {
        // code goes here
        int K = arr[0];
        List<Integer> ar = Arrays.stream(arr).boxed().collect(Collectors.toList()).subList(1, arr.length);
        int rows = K / 2;
        int x = 0;
        int[][] seats = new int[rows][2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 2; j++) {
                x++;
                if (ar.contains(new Integer(x))) {
                    seats[i][j] = 1;
                } else {
                    seats[i][j] = 0;
                }

            }
        }
        int seating = 0;
        for (int i = 0; i < rows - 1; i++) {
            if (seats[i][0] == 0 && seats[i][1] == 0) {
                seating++;
            }
            if (seats[i][0] == 0 && seats[i + 1][0] == 0) {
                seating++;
            }
            if (seats[i][1] == 0 && seats[i + 1][1] == 0) {
                seating++;
            }
        }

        if (seats[rows - 1][0] == 0 && seats[rows - 1][1] == 0) seating++;
        return seating;
    }

    static void holes(int i, int j, String[][] bithole, int number) {
        bithole[i][j] = String.valueOf(number);
        if (bithole[i][j - 1] == "0") {
            bithole[i][j - 1] = String.valueOf(number);
            holes(i, j - 1, bithole, number);
        }
        if (bithole[i][j + 1] == "0") {
            bithole[i][j + 1] = String.valueOf(number);
            holes(i, j + 1, bithole, number);
        }
        if (bithole[i - 1] != null && bithole[i - 1][j] == "0") {
            bithole[i - 1][j] = String.valueOf(number);
            holes(i - 1, j, bithole, number);
        }
        if (bithole[i + 1] != null && bithole[i + 1][j] == "0") {
            bithole[i + 1][j] = String.valueOf(number);
            holes(i + 1, j, bithole, number);
        }
        return;
    }

    public static String bitholeHoles(String[] strArr) {
        // code goes here
        int count = 2;
        String[][] bithole = Arrays.stream(strArr).map(x -> x.split("")).toArray(String[][]::new);
        for (int i = 0; i < bithole.length; i++) {
            for (int j = 0; j < bithole[i].length; j++) {

                holes(i, j, bithole, count++);
            }
        }
        return String.valueOf(count - 2);
    }


    static int getDiscounted(Map<String, List<String>> discountMaped, int price, List<String> discountList) {
        int min = price;
        for (String s : discountList) {
            if (!s.equalsIgnoreCase("EMPTY")) {
                List<String> ac = discountMaped.get(s);
                int dsc;
                switch (ac.get(0)) {
                    case "1":
                        dsc = price - (int) Math.ceil((price * Integer.parseInt(ac.get(1))) / 100.00);
                        break;
                    case "2":
                        dsc = price - Integer.parseInt(ac.get(1));
                        break;
                    default:
                        dsc = Integer.parseInt(ac.get(1));
                        break;
                }
                min = Math.min(min, dsc);
            }
        }
        return min;
    }

    static int findLowestPrices(List<List<String>> retails, List<List<String>> discounts) {
        int price = 0;
        Map<String, List<String>> discountMaped = new HashMap<>();
        for (List<String> ls : discounts) {
            discountMaped.put(ls.get(0), ls.subList(1, ls.size()));
        }
        for (List<String> ls : retails) {
            price += getDiscounted(discountMaped, Integer.parseInt(ls.get(0)), ls.subList(1, ls.size()));
        }
        return price;
    }

    public static void main(String[] args) {


    }

    public static void permutation(String str) {
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }

}

