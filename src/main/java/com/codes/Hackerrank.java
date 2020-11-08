package com.codes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Hackerrank {
    private static final Logger logger = LoggerFactory.getLogger(Hackerrank.class);


    /**
     * @param pattern
     * @param s
     * @return
     */
    static int binaryPatternMatching(String pattern, String s) {
        String vowels = "aeiouy";
        int count = 0;
        for (int i = 0; i < s.length() - pattern.length() + 1; i++) {
            String[] temp = s.substring(i, i + pattern.length()).split("");
            String temp1 = "";
            for (int j = 0; j < temp.length; j++) {
                if (vowels.contains(temp[j]))
                    temp1 += '0';
                else temp1 += '1';
            }
            if (temp1.equals(pattern)) count++;
        }
        return count;

    }

    static int[] circular(int[] n, int k, int[] q) {
        int[] x = new int[q.length];
        int inc = 0;
        if (n.length > k) k = k / n.length;
        List<Integer> integers = new LinkedList<>();
        for (int i = k + 1; i < n.length - 1; i++) {
            integers.add(n[i]);
        }
        for (int i = 0; i < k + 1; i++) {
            integers.add(n[i]);
        }

        for (int i : q) {
            x[inc++] = integers.get(i);
        }
        return x;
    }

    static int graphQuestionCodility(int[] a, int[] b, int N) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])) {
                Set<Integer> integers = map.get(a[i]);
                integers.add(b[i]);
                map.put(a[i], integers);
            } else {
                Set<Integer> integers = new HashSet<>();
                integers.add(b[i]);
                map.put(a[i], integers);
            }
            if (map.containsKey(b[i])) {
                Set<Integer> integers = map.get(b[i]);
                integers.add(a[i]);
                map.put(b[i], integers);
            } else {
                Set<Integer> integers = new HashSet<>();
                integers.add(a[i]);
                map.put(b[i], integers);
            }
        }

        for (int i : map.keySet()) {
            Set<Integer> list = map.get(i);
            Set<Integer> integers = new HashSet<>(list);
            for (int j : list) {
                if (map.containsKey(j))
                    integers.addAll(map.get(j));
            }
            map.put(i, integers);
        }
        HashMap<Set<Integer>, Integer> hashMap = new HashMap<>();

        int max = 1;
        for (int i = 0; i < a.length; i++) {
            Set<Integer> integers = new HashSet<>();
            integers.addAll(map.get(a[i]));
            integers.addAll(map.get(b[i]));

            if (hashMap.containsKey(integers)) {
                hashMap.put(integers, hashMap.get(integers) + 1);
            } else
                hashMap.put(integers, 1);
        }
        System.out.println(hashMap);
        return hashMap.values().stream().max(Integer::compareTo).get();

    }

    /**
     * @param s
     */
    static void palindrome(String s) {
        List<Character> characters = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : s.toCharArray()) {
            characters.add(c);
        }
        s.chars().distinct().forEach(x -> stringBuilder.append((char) x));
        boolean ad = true;
        char output = ' ';
        for (char c : stringBuilder.toString().toCharArray()) {
            if (Collections.frequency(characters, c) % 2 == 0) {
                continue;
            } else if (Collections.frequency(characters, c) % 2 != 0 && ad) {
                ad = false;
            } else {
                output = c;
                break;
            }
        }
        System.out.println(output);
    }

    static long xorSequence(long l, long r) {

        long next = 0;
        long ans = 0;
        for (long i = 1; i <= r; i++) {
            next = i ^ next;

            if (i >= l) {
                ans = next ^ ans;
            }
        }
        return ans;
    }

    static long theGreatXor(long x) {
        int va = 1;
        String s = Long.toBinaryString(x);
        return (1 << s.length()) - x - 1;

    }

    static int sansaXor(int[] arr) {
        if (arr.length % 2 == 0)
            return 0;
        else {
            int ans = arr[0];
            for (int i = 2; i < arr.length; i += 2) {
                ans ^= arr[i];
            }
            return ans;
        }
    }

    static void whatsNext(int[] arr) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            char[] ch = new char[arr[i]];
            if (i % 2 == 0) {
                Arrays.fill(ch, '1');
                sb.append(ch);
            } else {
                Arrays.fill(ch, '0');
                sb.append(ch);
            }
        }
        int index = sb.substring(0, sb.lastIndexOf("1")).lastIndexOf("0");
        char[] c = sb.toString().toCharArray();

        if (index == -1) {
            String s = "10";
            StringBuilder sx = new StringBuilder();
            sx.append(s);
            sx.append(sb.substring(1));
            sb = sx;
            c = sb.toString().toCharArray();
        } else {
            char ch = c[index];
            c[index] = c[index + 1];
            c[index + 1] = ch;
        }
        sb = new StringBuilder();
        int count = 0;
        char x = '1';
        for (char cx : c) {
            if (cx == x)
                count++;
            else {
                System.out.print(count + " ");
                count = 1;
                x = cx;
            }
        }
        System.out.println(count);
    }

    static String isBalanced(String s) {
        Stack<Character> sc = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '{' || c == '(' || c == '[')
                sc.push(c);
            else if (!sc.empty() && sc.peek() == '(' && c == ')') {
                sc.pop();
            } else if (!sc.empty() && sc.peek() == '{' && c == '}') {
                sc.pop();
            } else if (!sc.empty() && sc.peek() == '[' && c == ']') {
                sc.pop();
            } else if (c == '}' || c == ')' || c == ']') sc.push(c);
        }

        return sc.empty() ? "BALANCED" : "NOT BALANCED";
    }

    static int flatlandSpaceStations(int n, int[] c) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            int max = n;
            for (int j = 0; j < c.length; j++) {

                max = Math.abs(i - c[j]) < max ? Math.abs(i - c[j]) : max;
            }
            a[i] = max;
        }
        for (int i : c) {
            a[i] = 0;
        }
        Arrays.sort(a);
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != 0) {
                max = a[i];
                break;
            }
        }
        return a[n - 1];
    }

    public static boolean kforceBeautifulString(String s) {
        List<Character> ss = new ArrayList<>();
        for (char c : s.toCharArray()) {
            ss.add(c);
        }
        int x = 97;
        int[] a = new int[26];
        for (int i = 0; i < a.length; i++) {
            a[i] = Collections.frequency(ss, (char) x++);
        }
        int tt = a[0], count = 0;
        for (int i = 1; i < a.length; i++) {
            if (tt >= a[i]) {
                tt = a[i];
                count++;
            } else break;
        }
        return count == 25;
    }

    static int fairRations(int[] B) {
        int count = 0;
        boolean turn = true;
        for (int i = 0; i < B.length - 1; i++) {
            if (B[i] % 2 == 0) {
                if (turn) {
                    B[i] += 1;
                    B[i + 1] += 1;
                    i++;
                    count += 2;
                    turn = false;
                } else {
                    B[i] += 1;
                    B[i - 1] += 1;
                    i++;
                    count += 2;
                    turn = true;
                }
            }
        }

        return count;
    }

    static int[] jimOrders1(int[][] orders) {


        List<Integer> aak = new ArrayList<>();
        for (int i = 0; i < orders.length; i++) {
            int sum = orders[i][0] + orders[i][1];
            aak.add(sum);
        }
        List<Integer> aa = new ArrayList<>(aak);
        Collections.sort(aa, Collections.reverseOrder());
        Set<Integer> ss = new LinkedHashSet<>(aa);
        Iterator it = ss.iterator();
        int[] x = new int[orders.length];
        int l = orders.length;
        while (it.hasNext()) {
            int tem = (int) it.next();
            for (int i = 0; i < aak.size(); i++) {
                if (aak.get(i) == tem)
                    x[--l] = i + 1;
            }
        }

        return x;
    }


    static int solve(int[] a) {

        int count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] == a[j])
                    count++;
                else break;
            }
        }
        return count * 2;
    }

    static String isValid(String x) {
        List<Character> clist = new ArrayList<>();
        for (char c : x.toCharArray())
            clist.add(c);
        Set<Character> c = new TreeSet<>(clist);
        Iterator it = c.iterator();
        int odd = 0;
        int marker = Collections.frequency(clist, it.next());
        while (it.hasNext()) {
            int next = Collections.frequency(clist, it.next());
            if (next == marker) continue;
            else if (Math.abs(next - marker) < 2 && odd != 1)
                odd++;
            else {
                return "NO";
            }
        }
        return "YES";
    }

    static String twoStrings(String s1, String s2) {
        Set<Character> c1 = new TreeSet<>();
        for (char c : s1.toCharArray())
            c1.add(c);
        Set<Character> c2 = new TreeSet<>();
        for (char c : s2.toCharArray())
            c2.add(c);
        int len = c1.size();
        c1.retainAll(c2);
        if (c1.size() != 0)
            return "YES";

        return "NO";

    }

    static int[] closestNumbers(int[] arr) {
        Arrays.sort(arr);
        long min = arr[arr.length - 1];
        List<Integer> il = new ArrayList<>();
        for (int i = 0; i < arr.length - 1; i++) {
            long diff = arr[i + 1] - arr[i];
            if (min > diff) {
                il.clear();
                min = diff;
                il.add(arr[i]);
                il.add(arr[i + 1]);
            } else if (min == diff) {
                il.add(arr[i]);
                il.add(arr[i + 1]);

            }
            System.out.println(min);
        }
        int[] a = new int[il.size()];
        for (int i = 0; i < a.length; i++) {
            a[i] = il.get(i);
        }
        return a;


    }

    static int stringConstruction(String s) {
        Set<Character> se = new TreeSet<>();
        int step = 0;
        for (char c : s.toCharArray()) {
            if (se.add(c)) step++;
        }
        return step;
    }

    static String gameOfThrones(String s) {
        List<Character> c = new ArrayList<>();
        char[] cx = s.toCharArray();
        for (char ch : cx) {
            c.add(ch);
        }
        int odd = 0;
        Set<Character> cset = new TreeSet<>(c);
        Iterator it = cset.iterator();
        while (it.hasNext()) {
            if (Collections.frequency(c, it.next()) % 2 != 0)
                odd++;

        }
        if (odd == 0 || odd == 1)
            return "YES";
        else return "NO";
    }

    static String happyLadybugs(String b) {
        List<Character> c = new ArrayList<>();
        for (char ch : b.toCharArray()) {
            if (ch != '_')
                c.add(ch);
        }

        Set<Character> cs = new TreeSet<>(c);
        Iterator<Character> it = cs.iterator();
        while (it.hasNext()) {
            if (Collections.frequency(c, it.next()) < 2)
                return "NO";
        }
        return "YES";
    }

    static int howManyGames(int p, int d, int m, int s) {
        // Return the number of games you can buy
        if (p > s) return 0;
        int sum = 0, count = 0;
        while (p > m && p < s - sum) {
            sum += p;
            p -= d;
            count++;
        }
        if (p > s - sum) return count;


        return (count + (s - sum) / m);
    }


    static int beautifulTriplets(int d, Integer[] arr) {
        int tripletsCount = 0;
        List<Integer> ss = Arrays.asList(arr);
        for (int i = 0; i < arr.length; i++) {
            int x = 0;
            int sum = arr[i];
            while (x < 2) {
                if (ss.contains(sum + d)) {
                    sum += d;
                    x++;
                } else break;
            }
            if (x == 2)
                tripletsCount++;

        }
        return tripletsCount;
    }

    static void kaprekarNumbers(int p, int q) {
        StringBuilder sb = new StringBuilder();
        for (int i = p; i <= q; i++) {

            long sq = (long) Math.pow(i, 2);
            String s = String.valueOf(sq);
            int len = s.length();
            if (len % 2 != 0) {
                s = "0" + s;
                len++;
            }
            int half = len / 2;
            String l = s.substring(0, half);
            String r = s.substring(half, len);
            int sum = Integer.parseInt(l) + Integer.parseInt(r);
            if (sum == i) {
                sb.append(i + " ");
            }
        }
        if (sb.toString().length() == 0) System.out.println("INVALID RANGE");
        else System.out.println(sb.toString());
    }

    public static long taumBday(int b, int w, int bc, int wc, int z) {
        // Write your code here

        if (bc - wc > z) {
            return (w * wc) + ((wc + z) * b);
        } else if (wc - bc > z) {
            return (b * bc) + ((bc + z) * w);
        } else {
            return (b * bc) + (wc * w);
        }

    }

    static int[] acmTeam(String[] topic) {
        int[] a = new int[2];
        BigInteger[] b = new BigInteger[topic.length];
        for (int i = 0; i < topic.length; i++) {
            b[i] = new BigInteger(topic[i], 2);
        }
        int max = 0, maxCount = 0;
        for (int i = 0; i < topic.length - 1; i++) {
            for (int j = i + 1; j < topic.length; j++) {
                int count = b[i].or(b[j]).bitCount();
                if (max < count) {
                    max = count;
                    maxCount = 1;
                } else if (max == count) {
                    maxCount++;
                }
            }
        }
        a[0] = max;
        a[1] = maxCount;
        return a;
    }

    static long repeatedString(String s, long n) {
        long count = 0;
        for (char c : s.toCharArray()) {
            if (c == 'a') count++;
        }
        long div = n / s.length();
        long remain = n % s.length();
        count *= div;
        for (int i = 0; i < remain; i++) {
            if (s.charAt(i) == 'a') count++;
        }
        return count;

    }

    static String morganAndString(String a, String b) {
        int i = 0, j = 0;
        StringBuilder sb = new StringBuilder();
        while (i != a.length() && j != b.length()) {
            if (a.charAt(i) < b.charAt(j)) {
                sb.append(a.charAt(i));
                i++;
                continue;
            }
            if (a.charAt(i) > b.charAt(j)) {
                sb.append(b.charAt(j));
                j++;
                continue;
            }
            if (a.charAt(i) == b.charAt(j)) {
                if (i < j) {
                    sb.append(a.charAt(i));
                    i++;
                    continue;
                } else {
                    sb.append(b.charAt(j));
                    j++;
                    continue;
                }
            }
        }

        if (i == a.length()) sb.append(b.substring(j));
        else sb.append(a.substring(i));

        return sb.toString();
    }

    static int anagram(String s) {
        if (s.length() % 2 != 0) return -1;
        int len = s.length();
        int count = 0;
        String s1 = s.substring(0, len / 2);
        String s2 = s.substring(len / 2, len);
        int i = 0;
        for (; i < len / 2; i++) {
            if (s2.contains(Character.toString(s1.charAt(i)))) {
                String tem = Character.toString(s1.charAt(i));
                s2 = s2.replaceFirst(tem, "");
            } else count++;
        }

        return count;
    }

    static String[] weightedUniformStrings(String s, int[] queries) {
        Set<Integer> set = new TreeSet();
        int x = 0;
        char old = 'X';
        for (char c : s.toCharArray()) {
            if (c == old) {
                x += old - 'a' + 1;
                set.add(x);
            } else {
                old = c;
                x = old - 'a' + 1;
                set.add(x);

            }
        }
        String[] ans = new String[queries.length];
        for (int i = 0; i < queries.length; i++) {
            if (set.contains(queries[i])) ans[i] = "YES";
            else ans[i] = "NO";
        }
        return ans;
    }

    static int theLoveLetterMystery(String s) {
        int ans = 0;
        char[] c = s.toCharArray();
        for (int i = 0; i < Math.ceil(c.length / 2); i++) {
            ans = ans + Math.abs(c[i] - c[c.length - i - 1]);
        }
        return ans;

    }

    static String caesarCipher(String s, int k) {
        k = k % 26;
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            boolean upper = Character.isUpperCase(c);
            if (Character.isAlphabetic(c)) {
                char temp = (char) (Character.toLowerCase(c) + k);
                if (Character.isAlphabetic(temp))
                    sb.append(upper ? Character.toUpperCase(temp) : Character.toLowerCase(temp));
                else
                    sb.append(upper ? Character.toUpperCase((char) ('a' + (k - 1) - ('z' - Character.toLowerCase(c)))) : Character.toLowerCase((char) ('a' + (k - 1) - ('z' - Character.toLowerCase(c)))));

            } else sb.append(c);
        }
        return sb.toString();
    }

    static String balancedSums(List<Integer> arr) {

        int count = arr.get(0);
        int sum = 0;
        for (int i : arr) sum += i;
        if (sum - count == 0) return "YES";
        for (int i = 1; i < arr.size(); i++) {
            if (sum - arr.get(i) - count == count || sum - arr.get(i) == 0)
                return "YES";
            count += arr.get(i);
        }
        return "NO";

    }

    static int[] icecreamParlor(int m, int[] arr) {
        int[] ans = new int[2];
        for (int i = 0; i < arr.length; i++) {
            boolean breaker = false;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == m) {
                    ans[0] = i + 1;
                    ans[1] = j + 1;
                    breaker = true;
                    break;
                }
            }
            if (breaker) break;
        }
        Arrays.sort(ans);
        return ans;
    }

    static int beautifulBinaryString(String b) {
        int index = 0;
        while (b.contains("010")) {
            b = b.replaceFirst(
                    "010", "011");
            index++;
        }
        return index;
    }

    static int superDigit(String n, int k) {
        String s = new String(new char[k]).replace("\0", n);
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            ans += Character.getNumericValue(s.charAt(i));
        }
        if (ans < 10)
            return ans;
        else
            return superDigit(String.valueOf(ans), 1);

    }

    static long strangeCounter(long t) {
        int ini = 3;
        int count = 1;
        long i = ini;
        while (count != t) {

            if (i == 1) {
                ini = ini * 2;
                i = ini;
                count++;
            } else {
                i--;
                count++;
            }
        }
        return i;

    }

    static int[] permutationEquation(Integer[] p) {
        int[] a = new int[p.length];
        List<Integer> il = Arrays.asList(p);
        for (int i = 1; i <= p.length; i++) {
            int temp = il.indexOf(i);
            a[i - 1] = il.indexOf(temp + 1) + 1;
        }
        return a;
    }

    static int minimumAbsoluteDifference(int[] arr) {
        int[] distsort = Arrays.stream(arr).distinct().sorted().toArray();
        if (distsort.length != arr.length)
            return 0;

        int min = Math.abs(distsort[0] - distsort[1]);
        for (int i = 1; i < arr.length - 1; i++) {
            int temp = Math.abs(distsort[i] - distsort[i + 1]);
            min = Math.min(temp, min);
        }
        return min;
    }


    static long marcsCakewalk(Integer[] calorie) {
        List<Integer> ilist = Arrays.asList(calorie);
        ilist.sort(Collections.reverseOrder());
        double ans = 0;
        for (int i = 0; i < ilist.size(); i++) {
            ans = ans + (Math.pow(2, i) * ilist.get(i));
        }
        return (long) ans;
    }

    static String twoArrays(int k, Integer[] A, Integer[] B) {
        List<Integer> alist = Arrays.asList(A);
        List<Integer> blist = Arrays.asList(B);
        Collections.sort(alist);
        blist.sort(Collections.reverseOrder());
        int count = 0;
        for (int i = 0; i < A.length; i++) {
            System.out.println(alist.get(i) + " " + blist.get(i));
            if (alist.get(i) + blist.get(i) >= k) count++;

        }
        return (count == alist.size()) ? "yes" : "no";
    }

    static int toys(Integer[] w) {
        Arrays.sort(w);
        List<Integer> ilist = Arrays.asList(w);
        int containerCount = 1;
        int min = Collections.min(ilist);
        for (int i = 0; i < w.length; i++) {
            if ((w[i] > min + 4)) {
                min = Collections.min(ilist.subList(i, ilist.size()));
                containerCount++;
            }
        }
        return containerCount;

    }

    static int maximumToys(int[] prices, int k) {
        int count = 0, amount = 0;
        Arrays.sort(prices);
        int i = 0;
        while (prices[i] < k - amount) {
            amount += prices[i];
            count++;
            i++;
        }
        return count;
    }

    static String counterGame(long n) {
        int i = 0;
        while (n > 0) {

            if ((n & (n - 1)) == 0) {
                n /= 2;
            } else {
                n -= getNextPowerOfTwo(n);
            }

            i = i == 0 ? 1 : 0;
        }
        return i == 0 ? "l" : "r";

    }

    static double getNextPowerOfTwo(long n) {
        int count = 0;
        while (n > 1) {
            n >>= 1;
            count++;
        }
        return Math.pow(2, count);
    }

    static int[] countingSort(int[] arr) {
        int[] ans = new int[100];
        for (int i : arr) {
            ans[i--]++;
        }
        int i = 0, k = 0;
        while (i < 100) {
            if (ans[i] != 0) {
                for (int j = 0; j < ans[i]; j++) {
                    k++;
                }
            }
            i++;
        }
        return ans;

    }

    static int[] quickSort(int[] arr) {
        int[] left = new int[arr.length - 1];
        int l = 0;
        int[] right = new int[arr.length - 1];
        int r = 0;
        int[] middle = new int[arr.length - 1];
        int m = 1;

        middle[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (middle[0] == arr[i]) {
                middle[m] = arr[i];
                m++;
            }
            if (middle[0] > arr[i]) {
                left[l] = arr[i];
                l++;
            }
            if (middle[0] < arr[i]) {
                right[r] = arr[i];
                r++;
            }
        }
        int[] a = new int[arr.length];
        int a1 = 0;
        for (int i = 0; i < a.length - 1; i++) {
            if (left[i] == 0) break;
            a[a1] = left[i];
            a1++;
        }
        for (int i = 0; i < a.length - 1; i++) {
            if (middle[i] == 0) break;

            a[a1] = middle[i];
            a1++;
        }
        for (int i = 0; i < a.length - 1; i++) {
            if (right[i] == 0) break;
            a[a1] = right[i];
            a1++;
        }

        return a;

    }

    static void insertionSort2(int n, int[] arr) {
        for (int i = 1; i < n; i++) {
            int k = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > k) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = k;
            for (int l : arr)
                System.out.print(l + " ");
            System.out.println();
        }

    }

    static int[] serviceLane(int n, int[][] cases, int[] width) {
        int[] x = new int[cases.length];
        for (int i = 0; i < cases.length; i++) {
            int start = cases[i][0];
            int end = cases[i][1];
            int min = Arrays.stream(width).max().getAsInt();
            for (int j = start; j <= end; j++) {
                min = Math.min(min, width[j]);
            }
            x[i] = min;
        }
        return x;
    }

    static int squares(int a, int b) {
        List<Integer> il = new ArrayList<>();
        double sb = 0;
        for (int i = a; i <= b; i++) {
            sb = Math.sqrt(i);
            if (sb == Math.floor(sb)) {
                il.add((int) sb);
                sb = (int) sb;
                break;
            }
        }
        sb++;
        while (Math.pow(sb, 2) <= b) {
            il.add((int) sb);
            sb++;
        }
        return il.size();
    }

    static int minimumDistances(int[] a) {
        int min = a.length;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] == a[j])
                    min = Math.min(Math.abs(i - j), min);
            }
        }
        return min != a.length ? min : -1;
    }

    static int equalizeArray(int[] arr) {
        Map<Integer, Integer> iim = new HashMap<>();
        for (int i : arr)
            if (iim.containsKey(i)) iim.put(i, iim.get(i) + 1);
            else iim.put(i, 1);
        int max = Collections.max(iim.values());
        return arr.length - max;

    }

    static Integer[] cutTheSticks(int[] arr) {
        List<Integer> integers = new ArrayList<>();
        for (int i : arr)
            integers.add(i);
        List<Integer> ans = new ArrayList<>();
        while (!integers.isEmpty()) {
            int min = Collections.min(integers);
            int len = integers.size();
            ans.add(len);
            for (int i = 0; i < len; i++) {
                int temp = integers.remove(0);
                if (temp - min != 0) integers.add(temp - min);
            }

        }

        return ans.toArray(new Integer[0]);
    }

    static int jumpingOnClouds(int[] c, int k) {
        int energy = 100;
        int i = 0;
        int n = c.length;
        while (energy != 0) {
            i = (i + k) % n;

            int tep = c[i];
            if (tep == 0)
                energy--;
            else energy -= 3;
            if (i == 0) break;
        }
        return energy;
    }

    static long arrayManipulation(int n, int[][] queries) {
        int[] a = new int[n];

        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];
            int inc = query[2];
            for (int j = start; j <= end; j++) {
                a[j] += inc;
            }

        }
        Arrays.sort(a);
        return a[n - 1];
    }

    static int[] rotateLeftByK(int[] a, int k) {
        int len = a.length;

        int[] te = new int[k];
        for (int i = 0, temp = 0; i < k; i++) {
            te[i] = a[temp++];
        }

        for (int i = 0; i < len - k; i++)
            a[i] = a[i + k];

        for (int i = len - 1, x = te.length; x > 0; i--) {
            a[i] = te[--x];
        }
        return a;

    }

    static int[] rotateRightByK(int[] a, int k) {
        int len = a.length;

        int[] te = new int[k];
        for (int i = te.length - 1, temp = len; i >= 0; i--) {
            te[i] = a[--temp];
        }

        for (int i = len - 1; i > k - 1; i--)
            a[i] = a[i - k];

        for (int i = 0; i < te.length; i++) {
            a[i] = te[i];
        }
        return a;
    }

    static int[] circularArrayRotation(int[] a, int k, int[] queries) {

        a = rotateRightByK(a, k);

        int[] x = new int[queries.length];
        int j = 0;
        for (int i : queries) {
            x[j] = a[i];
            j++;
        }

        return x;
    }

    static int saveThePrisoner(int p, int s, int i) {
        int ans = (s + i - 1) % p;
        return ans == 0 ? p : ans;
    }

    static void electronicShop(int budget, int[] key, int[] drives) {
        int max = 0;
        for (int i : key) {
            for (int j : drives) {
                int temp = i + j;
                max = temp <= budget && temp > max ? temp : max;
            }
        }
        logger.info(String.valueOf(max));
    }

    static void funny(int s) {

        boolean century = (s % 100 == 0);

        if (s > 1918) {
            if (s % 400 == 0)
                System.out.println("12.09." + s);
            else if (!century && s % 4 == 0) System.out.println("12.09." + s);
            else System.out.println("13.09." + s);

        } else {

            if (s % 4 == 0)
                System.out.println("12.09." + s);
            else System.out.println("13.09." + s);
        }
    }

    public static String stringsXOR(String s, String t) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(i))
                res.append("0");
            else
                res.append("1");
        }

        return res.toString();
    }


    static int hurdleRace(int[] height) {
        Arrays.sort(height);

        return height[(height.length - 1) / 2];
    }

    public static String getString(int i) {
        String[] a = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirt", "fourt", "fift", "sixt", "eight", "nint", "twent"};
        List<String> str = Arrays.asList(a);
        return str.get(i - 1);
    }

    public static void convertTime(int h, int m) {
        if (m == 0)
            System.out.println(getString(h) + " O' clock");
        else if (m > 0 && m < 30)
            System.out.println((m > 13) ? getString(m) : getString(m) + "een" + "minutes past " + getString(h));
        else System.out.println();
    }

    static int jumpingOnClouds(int[] c) {
        int count = 0, i = 0;
        boolean notImpossible = true;
        while (i != c.length - 1 && notImpossible) {
            if (i + 2 <= c.length - 1 && c[i + 2] == 0) {
                i = i + 2;
                count++;
            } else if (c[i + 1] == 0) {
                i++;
                count++;
            } else notImpossible = false;
        }
        return count;

    }

    static int palindromeIndex(String s) {
        for (int i = 0; i < s.length(); i++) {
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.delete(i, i + 1);
            if (stringBuilder.toString().equals(stringBuilder.reverse().toString())) {
                return i;
            }
        }
        return -1;
    }

    static int hackerlandRadioTransmitters(int[] x, int k) {
        Arrays.sort(x);
        int temp = x[0] + 2 * k, count = 1;
        for (int i = 0; i < x.length; i++) {
            if (!(x[i] <= temp)) {
                temp = x[i] + 2 * k;
                count++;
            }
        }
        return count;

    }


    public static int pickingNumbers(List<Integer> a) {
        int max = 0;
        int current = 0;
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i : a) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        if (map.size() == 1) return map.get(a.get(0));
        List<Integer> integers = new ArrayList<>(map.keySet());
        for (int i = 0; i < integers.size() - 1; i++) {
            int i1 = integers.get(i + 1);
            int i2 = integers.get(i);
            if (i1 - i2 < 2) {
                current = map.get(i1) + map.get(i2);
            }
            max = Math.max(current, max);
        }


        return Math.max(Collections.max(map.values()), max);
    }

    static int[] climbingLeaderboard(int[] scores, int[] alice) {
        int[] res = new int[alice.length];

        int[] dense = Arrays.stream(scores).distinct().mapToObj(Integer::new).mapToInt(Integer::intValue).toArray();

        int x = 0;
        int cycle = dense.length;
        for (int i : alice) {
            for (int j = 0; j < cycle; j++) {
                if (dense[j] <= i) {
                    res[x] = j + 1;
                    cycle = j + 1;
                    break;
                }
            }
            if (res[x] == 0)
                res[x] = cycle + 1;
            x++;

        }
        return res;

    }

    static String appendAndDelete(String s, String t, int k) {

        while (k != 0 || s.equals(t)) {
            if (s.length() >= t.length()) {
                String temp = s.substring(0, t.length());
                if (temp.equals(t)) {
                    k -= s.substring(t.length()).length();
                    break;
                } else {
                    t = t.substring(0, t.length() - 1);
                    k--;
                }
            } else {
                t = t.substring(0, t.length() - 1);
                k--;
                if (s.equals(t))
                    break;
            }
        }

        return k == 0 || k % 2 != 0 ? "Yes" : "No";
    }

    static String biggerIsGreater(String w) {
        char[] c = w.toCharArray();
        for (int i = w.length() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (c[i] > c[j]) {
                    char tem = c[i];
                    c[i] = c[j];
                    c[j] = tem;
                    String jk = new String(c);
                    char[] x = jk.substring(j + 1).toCharArray();
                    Arrays.sort(x);
                    return jk.substring(0, jk.length() - x.length) + new String(x);
                }
            }

        }

        return "no answer";

    }

    public static int arithSlice(int[] A) {
        int[] dp = new int[A.length];
        int count = 0;
        for (int i = 2; i < A.length; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                dp[i] = dp[i - 1] + 1;
                count += dp[i];
            }
        }
        return count;
    }

    public static int arithSlice1(int[] A) {
        int count = 0;
        int len = 0; // "length of subarray to be considered - 2"

        for (int i = 2; i < A.length; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2])
                len++; // increment length of current potential subarray
            else {
                count += (len * (len + 1)) / 2; // number of subarrays for the current length
                len = 0; // reset the length since the sequence breaks
            }
        }

        count += (len * (len + 1)) / 2; // sub arrays for the last length

        return count;
    }

    public static int nonDivisibleSubset(int k, List<Integer> s) {

        int[] freq = new int[k];
        Arrays.fill(freq, 0);
        for (int i : s) {
            freq[i % k]++;
        }

        int res = Math.min(freq[0], 1);
        if (k % 2 == 0) freq[k / 2] = Math.min(freq[k / 2], 1);
        for (int i = 1; i < k / 2; i++) {
            res += Math.max(freq[i], freq[k - i]);
        }
        return res;

    }

    static int workbook(int n, int k, int[] arr) {
        int queno, pageNo = 1, count = 0;
        for (int i = 0; i < n; i++) {
            queno = 1;
            for (int j = 1; j <= arr[i]; j++) {
                if (queno == pageNo) count++;
                if (queno % k == 0 && j != arr[i]) {
                    pageNo++;
                }
                queno++;
            }
            pageNo++;
        }
        return count;
    }


    static int consecutiveThree(int num) {
        int score = 0;
        int current = 0;
        while (num > 0) {
            int d = num % 10;
            num = num / 10;
            if (d != 3 && current > 1) {

                score += (current - 1) * 4;
                current = 0;
            }
            if (d == 3) {
                current++;
            }
            if (d != 3 && current == 1) {
                current = 0;
            }

        }
        if (current > 1) {
            score += (current - 1) * 4;
        }
        return score;
    }

    public static Map<String, Integer> processData(ArrayList<String> array) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> retVal = new HashMap<>();
        for (String s : array) {

            String[] x = s.split(", ");
            if (map.containsKey(x[2])) {
                if (map.get(x[2]) < Integer.parseInt(x[0])) {
                    map.put(x[2], Integer.parseInt(x[0]));
                    retVal.put(x[2], Integer.parseInt(x[3]));
                }
            } else {
                map.put(x[2], Integer.parseInt(x[0]));
                retVal.put(x[2], Integer.parseInt(x[3]));
            }
        }
        return retVal;
    }

    public static void printArray(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = a[i].length - 1; j >= 0; j--) {
                sb.append(a[j][i] + " ");
            }

            System.out.println(sb.toString().trim());
        }
    }

    static int getZeroOnes(String s, String zeroOrOne) {
        if (zeroOrOne.equals("1")) {
            return s.replace("0", "").length();
        }
        return s.replace("1", "").length();
    }

    static boolean arrayContains(int[] a, int v) {

        for (int i : a) {
            if (i == v) return true;
        }
        return false;
    }

    static int[][] divideArrayInTwoDistinctArray(int[] a) {

        int[][] res = new int[2][];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : a) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else map.put(i, 1);
        }
        int i1 = 0, i2 = 0;
        int[] a1 = new int[a.length / 2];
        int[] a2 = new int[a.length / 2];

        for (Map.Entry<Integer, Integer> ie : map.entrySet()) {
            if (ie.getValue() == 2) {
                a1[i1++] = ie.getKey();
                a2[i2++] = ie.getKey();
            } else if (i1 > i2) {
                a2[i2++] = ie.getKey();
            } else a1[i1++] = ie.getKey();
        }
        System.out.println(map);
        res[0] = a1;
        res[1] = a2;
        return res;

    }

    public static String getIntersection(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        for (String s : s1.split("")) {
            if (!s2.contains(s)) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static List<String> compareFriends(List<String> frndsList) {
        List<String> strings = new ArrayList<>();
        Set<String> strings1 = new HashSet<>();
        Collections.sort(frndsList);
        for (String s : frndsList) {
            if (!strings1.contains(s)) {
                strings.add(s);
                String[] strings2 = s.split(",");
                strings1.add(s);
                strings1.add(strings2[1] + "," + strings2[0]);
            }
        }
        return strings;
    }

    public int[] solution(int N) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    static int findPlatform(List<Integer> arrl, List<Integer> depl, int waitTime, int minplanes) {
        int[] arr = arrl.stream().mapToInt(Integer::intValue).toArray();
        int[] dep = depl.stream().mapToInt(Integer::intValue).toArray();
        int plat_needed = minplanes + 1, result = minplanes + 1;
        int i = 1, j = 0;
        if (arrl.size() == 0) return minplanes;
        if (depl.size() == 0) return arrl.size() + minplanes;
        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) {
                plat_needed++;
                i++;
                System.out.printf("%d,%d%n", i, j);
            } else if (arr[i] > dep[j]) {
                plat_needed--;
                j++;
                System.out.printf("%d,%d%n", i, j);
            }
            if (plat_needed > result)
                result = plat_needed;
        }
        return result;
    }

    public static int solution(String S) {
        Stack<Integer> st = new Stack<>();
        String operations = "DUP, POP, +, -";
        for (String s : S.split(" ")) {
            if (operations.contains(s)) {
                switch (s) {
                    case "DUP":
                        if (!st.empty()) {
                            st.push(st.peek());
                        }
                        break;
                    case "POP":
                        if (!st.empty()) {
                            st.pop();
                        } else return -1;
                        break;
                    case "+":
                        if (!st.empty()) {
                            int x = st.pop();
                            if (!st.empty()) {
                                st.push(x + st.pop());
                            } else return -1;
                        } else return -1;
                        break;
                    case "-":
                        if (!st.empty()) {
                            int x = st.pop();
                            if (!st.empty()) {
                                st.push(x - st.pop());
                            } else return -1;
                        } else return -1;
                        break;

                }
            } else {
                st.push(Integer.parseInt(s));
            }
        }

        return !st.empty() ? st.peek() : -1;
    }

    public static int minimumTime(List<Integer> ability, long processes) {
        // Write your code here
        int count = 0;
        System.out.print(ability.size() + " " + processes);
        Collections.sort(ability);
        while (processes > 0) {
            int max = ability.get(ability.size() - 1);
            for (int i = ability.size() - 1; i > (max / 2); i--) {
                processes -= i;
                ability.remove(new Integer(i));
                ability.add(i / 2);
                count++;
            }
        }

        return count;
    }

    static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(o2.length(), o1.length());
        }
    }


    static int formula(int totalbeforetrue, int k) {
        return totalbeforetrue + (totalbeforetrue / k) * 5 + 1;
    }

    static void solution(List<String> l, String right) {
        Map<Integer, List<String>> result = l.stream().collect(Collectors.groupingBy(String::length));
        int k = 1;
        int totalbeforeTruemax = result.entrySet().stream().map(x -> {
            if (x.getKey() > right.length())
                return x.getValue().size();
            else return 0;
        }).reduce(Integer::sum).orElse(0);
        int tbtmin = totalbeforeTruemax + result.get(right.length()).size() - 1;
        int maxformulae = formula(totalbeforeTruemax, k);
        int min = formula(tbtmin, k);
        System.out.println(maxformulae + " " + min);
    }

    String balancedStringWithWildcard(String query) {
        Stack<Character> st = new Stack<>();
        int star = 0;

        for (char c : query.toCharArray()) {
            if (c == '*') {
                star++;
                continue;
            }
            if (c == '(') {
                st.push(c);
            } else if (!st.empty() && c == ')') {
                st.pop();
            } else if (c == ')' && star > 0) {
                star--;
            } else {
                return "Not Balanaced";
            }
        }
        if (st.empty() || star - st.size() >= 0) {
            return "Balanced";
        } else return "Not Balanced";
    }

    /**
     * @param ids
     * @param dates
     */
    void averageLactationPeriod(List<String> ids, List<String> dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, List<Date>> stringListMap = new HashMap<>();
        try {
            for (int i = 0; i < ids.size(); i++) {
                if (stringListMap.containsKey(ids.get(i))) {
                    List<Date> dates1 = stringListMap.get(ids.get(i));
                    dates1.add(sdf.parse(dates.get(i)));
                    stringListMap.put(ids.get(i), dates1);
                } else {
                    stringListMap.put(ids.get(i), new ArrayList<>(Collections.singletonList(sdf.parse(dates.get(i)))));
                }
            }
            Map<String, Double> result = new HashMap<>();
            for (String s : stringListMap.keySet()) {
                List<Date> dateSet = stringListMap.get(s);
                Collections.sort(dateSet);
                double res = 0;
                for (int i = 1; i < dateSet.size(); i++) {
                    long ms = dateSet.get(i).getTime() - dateSet.get(i - 1).getTime();
                    res += (ms / (1000 * 60 * 60 * 24)) % 365;
                }
                result.put(s, res / (dateSet.size() - 1));
            }
            result.entrySet().stream().sorted((Map.Entry.<String, Double>comparingByValue().reversed()))
                    .forEach(x -> logger.info("{} {}", x.getKey(), x.getValue()));

        } catch (Exception e) {
            logger.error("Error Occured", e);
        }

    }


    public static int minimumFlips(String s) {
        char c = '0';
        int count = 0;
        for (char x : s.toCharArray()) {
            if (c != x) {
                count++;
                c = x;
            }
        }
        return count;
    }


    

}
