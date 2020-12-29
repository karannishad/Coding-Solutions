package com.codes;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class WordMachine {
    public static final int MAX = 0xFFFFF;
    public static final int MIN = 0;
    private final Stack<Integer> stack = new Stack<>();

    public int solution(String S) {
        List<String> operations = Arrays.asList("DUP", "POP", "+", "-");
        try {
            for (String s : S.split(" ")) {
                if (operations.contains(s)) {
                    switch (s) {
                        case "DUP": {
                            hasElements(1);
                            stack.push(stack.peek());
                            break;
                        }
                        case "POP": {
                            stack.pop();
                        }
                        case "+": {
                            hasElements(2);
                            push(stack.pop() + stack.pop());
                            break;
                        }
                        case "-": {
                            hasElements(2);
                            push(stack.pop() - stack.pop());
                            break;
                        }
                    }
                } else {
                    stack.push(Integer.parseInt(s));
                }
            }
            return stack.pop();
        } catch (Exception e) {
            return -1;
        }
    }

    private void push(int i) {
        if (i < MIN || i > MAX) {
            throw new IllegalArgumentException("Underflow/overflow");
        }
        stack.push(i);
    }

    private void hasElements(int i) {
        if (stack.size() < i) {
            throw new IllegalArgumentException("Too few elements available");
        }
    }
}
