package com.codes;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Runner implements Serializable {
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

    static int getAndProduct(List<Integer> ar) {

//        List<Integer> ar = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3}));
        int ans = 0;
        for (int i = 0; i < ar.size(); i++) {
            int cur = ar.get(i);
            for (int j = i; j < ar.size(); j++) {
                cur &= ar.get(j);
                ans += cur;
            }
        }
        return ar.stream().reduce((a, b) -> a & b).orElse(0);
    }

    public static String counterToLeftMod() {
        List<Integer> a = new ArrayList<>();
        a.addAll(Arrays.asList(2, 1, 3));
        int[] ans = new int[a.size()];
        int sum = a.get(0);
        for (int i = 1; i < a.size(); i++) {
            ans[i] = a.get(i) * i - sum;
            sum += a.get(i);
        }
        return (Arrays.toString(ans));
    }

    static String[] getMovieTitles(String substr) {
        int total_pages = 1;
        List<String> strings = new ArrayList<>();
        try {
            for (int i = 1; i <= total_pages; i++) {
                String urlString = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr + "&page=" + i;
                URL url = new URL(urlString);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;

                while ((line = br.readLine()) != null) {
                    Response json = new Gson().fromJson(line, Response.class);
                    total_pages = json.gettotal_pages();
                    for (MovieRecord movieRecord : json.getData()) {
                        strings.add(movieRecord.getTitle());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings.stream().sorted().toArray(String[]::new);
    }

    static String uniqueChar(String s) {
        return Arrays.stream(s.split("")).distinct().sorted().collect(Collectors.joining(""));
    }

    static int shortestSubstring(String shortest) {
        int left = 0;
        String unique = uniqueChar(shortest);
        int right = unique.length();
        int lenght = shortest.length();
        while (left < right && right <= shortest.length()) {
            if (uniqueChar(shortest.substring(left, right)).equalsIgnoreCase(unique)) {
                lenght = Math.min(right - left, lenght);
                left++;
            } else {
                right++;
            }
        }
        return lenght;
    }

    public static String autocorrect(String input) {

        return Arrays.stream(input.split(" ")).map(s -> {
            String x = s;
            if (s.matches("(?i)u.?|(?i)yo[u]+.?")) {
                x = "your client";
            }
            if (s.endsWith(".")) {
                x += ".";
            }
            return x;
        }).collect(Collectors.joining(" "));

    }

    static Function<String, CharacterCount> getDistinctCount() {
        Function<String, CharacterCount> function = s -> new CharacterCount(s, Arrays.stream(s.split("")).distinct().toArray().length);
        return function;
    }

    static Predicate<String> nameStartsWithPrefix(String s) {
        Predicate<String> p = x -> x.startsWith(s);
        return p;
    }

    public static int majorityElement(int[] nums) {

        int majorityElement = nums[0];
        int votesCount = 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == majorityElement) {
                //if cur element is majoruty element
                votesCount++;

            } else {
                //if cur element is not majority element
                votesCount--;
            }

            if (votesCount == 0) {

                majorityElement = nums[i];
                votesCount = 1;

            }


        }

        return majorityElement;
    }

    static int segregateEvenOdd(int[] arr) {
        /* Initialize left and right indexes */
        int count = 0;
        int left = 0, right = arr.length - 1;
        while (left < right) {
            /* Increment left index while we see 0 at left */
            while (arr[left] % 2 == 0 && left < right)
                left++;

            /* Decrement right index while we see 1 at right */
            while (arr[right] % 2 == 1 && left < right)
                right--;

            if (left < right) {
                count++;
                /* Swap arr[left] and arr[right]*/
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
        return count;
    }

    static boolean checkIfAnagramIsAPalindrome(String s) {
        int[] chars = new int[26];
        for (char c : s.toCharArray()) {
            chars[c - 'a']++;
        }
        boolean onlyOne = false;
        for (int i : chars) {
            if (i % 2 != 0) {
                if (onlyOne) {
                    return false;
                } else onlyOne = true;
            }

        }
        return true;
    }

    static int noOfCharsFailingPalindrome(String s) {
        int[] chars = new int[26];
        for (char c : s.toCharArray()) {
            chars[c - 'a']++;
        }
        int count = 0;
        for (int i : chars) {
            if (i % 2 != 0) {
                count++;
            }

        }
        return count;
    }

    static int addToPalindrome(String s) {
        if (checkIfAnagramIsAPalindrome(s)) {
            if (s.length() % 2 == 0) {
                return 27;
            } else return 2;
        } else {
            int count = noOfCharsFailingPalindrome(s);
            if (count < 3) {
                return 2;
            } else return 0;

        }
    }

    static List<String> fileNameing(List<String> names) {
        List<String> ans = new ArrayList<>();
        Map<String, Integer> stringStringMap = new HashMap<>();
        for (String s : names) {
            String toPut = s;
            while (stringStringMap.containsKey(toPut)) {
                toPut = String.format("%s(%d)", s, stringStringMap.get(toPut) + 1);
            }
            stringStringMap.put(toPut, 1);
            ans.add(toPut);
        }
        return ans;
    }

    public static int[][] diagonalSort(int[][] mat) {
        int len = mat.length;
        for (int i = 0; i < len; i++) {
            Integer[] sorted = getElement(mat, i);
            putElement(mat, i, sorted);
        }
        int row = mat[0].length;
        for (int i = 1; i < mat[0].length; i++) {

            putElement(mat, row, i, getElement(mat, row, i));
        }
        return mat;
    }

    static Integer[] getElement(int[][] mat, int row, int col) {
        List<Integer> list = new ArrayList<>();
        for (int i = col; i < mat[0].length; i++) {
            list.add(mat[--row][i]);
        }
        return list.stream().sorted().toArray(Integer[]::new);
    }

    static void putElement(int[][] mat, int row, int col, Integer[] an) {
        int x = 0;
        for (int i = col; i < mat[0].length; i++) {
            mat[--row][i] = an[x++];
        }
    }

    static Integer[] getElement(int[][] mat, int row) {
        List<Integer> list = new ArrayList<>();
        int col = 0;
        for (int i = row; i >= 0; i--) {
            list.add(mat[i][col++]);
        }
        return list.stream().sorted().toArray(Integer[]::new);
    }

    static void putElement(int[][] mat, int row, Integer[] an) {
        int col = 0;
        for (int i = row; i >= 0; i--) {
            mat[i][col] = an[col];
            col++;
        }
    }

    static int minimumSwaps(int[] a) {

        int even = 0;
        for (int i : a) {
            if (i % 2 == 0) even++;
        }
        for (int i = 0; i < a.length / 2; i++) {
            if (a[i] % 2 == 0) {
                even--;
            }
        }
        return even;
    }

    static int totalMinimumWaitingTime(int[] arr) {
        System.out.println(Arrays.toString(arr));
        int ans = 0;
        Arrays.sort(arr);
        int[] waitList = new int[arr.length];
        waitList[0] = 0;
        for (int i = 1; i < arr.length; i++) {
            waitList[i] = waitList[i - 1] + arr[i - 1];
            ans += waitList[i];
        }
        return ans;
    }

    static int[] generateRandomIntArray(int size, int low, int high) {
        Random random = new Random();
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = random.nextInt(high) + low;
        }
        return ans;
    }

    static int minimumCost(int[] arr) {
        System.out.println(Arrays.toString(arr));
        int count = 0;
        List<Integer> al = Arrays.stream(arr).boxed().collect(Collectors.toList());
        while (al.size() >= 2) {
            Collections.sort(al);
            int ans = al.remove(0) + al.remove(0);
            count += ans;
            al.add(ans);
        }
        return count;
    }

    public static String minWindow(String s, String t) {
        if (s == null || s.length() < t.length() || s.length() == 0) {
            return "";
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (char c : t.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        int left = 0;
        int minLeft = 0;
        int minLen = s.length() + 1;
        int count = 0;
        for (int right = 0; right < s.length(); right++) {
            if (map.containsKey(s.charAt(right))) {
                map.put(s.charAt(right), map.get(s.charAt(right)) - 1);
                if (map.get(s.charAt(right)) >= 0) {
                    count++;
                }
                while (count == t.length()) {
                    if (right - left + 1 < minLen) {
                        minLeft = left;
                        minLen = right - left + 1;
                    }
                    if (map.containsKey(s.charAt(left))) {
                        map.put(s.charAt(left), map.get(s.charAt(left)) + 1);
                        if (map.get(s.charAt(left)) > 0) {
                            count--;
                        }
                    }
                    left++;
                }
            }
        }
        if (minLen > s.length()) {
            return "";
        }

        return s.substring(minLeft, minLeft + minLen);
    }

    static int getMaxValue(List<Integer> s) {
        Collections.sort(s);
        int[] a = s.stream().mapToInt(i -> i).toArray();

        a[0] = 1;

        for (int i = 1; i < a.length; i++) {
            if (a[i] - a[i - 1] > 1) {
                a[i] = a[i - 1] + 1;
            }
        }
        return a[a.length - 1];

    }

    static int maxDiff(int[] arr) {

        int n = arr.length;
        int min_element = arr[0];
        int diff = arr[1] - arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] - min_element > diff)
                diff = arr[i] - min_element;
            if (arr[i] < min_element)
                min_element = arr[i];
        }
        return diff;
    }

    static void printAll(String s) {
        if (s.length() == 0) return;
        System.out.println(s);
        printAll(s.substring(0, s.length() - 1));
        printAll(s.substring(1));
    }

    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    static boolean perfectString(String s, int count) {

        Map<Character, Integer> chars = new HashMap<>();
        for (char c : s.toCharArray()) {
            chars.put(c, chars.containsKey(c) ? chars.get(c) + 1 : 1);
        }

        if (chars.values().stream().distinct().count() != 1) return false;
        return chars.get(s.charAt(0)) == count;
    }

    public static void main(String[] args) {
        System.out.println("sad");
    }

    void read() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("clientkeystore");
            System.out.println(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Response implements Serializable {

        private int total_pages;

        private List<MovieRecord> data;

        public Response() {
        }

        public Response(int total_pages, List<MovieRecord> data) {
            this.total_pages = total_pages;
            this.data = data;
        }

        public int gettotal_pages() {
            return total_pages;
        }

        public List<MovieRecord> getData() {
            return data;
        }

    }

    static class MovieRecord implements Serializable {

        private final String Title;

        public MovieRecord(String title) {
            Title = title;
        }

        public String getTitle() {
            return Title;
        }

    }


    // Use this code snippet in your app.
// If you need more information about configurations or implementing the sample code, visit the AWS docs:
// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites

    static class CharacterCount {
        private final String name;
        private final Integer distinctCount;

        public CharacterCount(String name, Integer distinctCount) {
            this.name = name;
            this.distinctCount = distinctCount;
        }

        @Override
        public String toString() {
            return "CharacterCount{" +
                    "name='" + name + '\'' +
                    ", distinctCount=" + distinctCount +
                    '}';
        }
    }
}



