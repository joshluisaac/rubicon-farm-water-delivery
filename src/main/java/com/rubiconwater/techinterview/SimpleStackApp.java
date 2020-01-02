package com.rubiconwater.techinterview;

public class SimpleStackApp {

    public static void main(String[] args) {

        SimpleStack<String> stack = new SimpleStack<>(new CustomQueue<>());
        stack.push("1");
        stack.push("2");
        stack.push("3");
        String popped = stack.pop();
        stack.push("4");
        stack.push("5");
        stack.push("6");
        popped = stack.pop();
    }

}
