package com.rubiconwater.techinterview;

import java.util.LinkedList;
import java.util.Queue;

public class CustomStack<T> {

  private Queue<T> queue = new LinkedList<>();
  private Queue<T> queueBackup = new LinkedList<>();

  // pushes an item to the stack
  boolean push(T t) {
    return queue.add(t);
  }

  T pop() {
    T t = null;
    int size = queue.size();
    for (int i = 0; i < size; i++) {
      t = queue.poll();
      if (i != (size - 1)) {
        queueBackup.add(t);
      }
    }
    queue = queueBackup;
    return t;
  }

  public static void main(String[] args) {
    CustomStack<String> customStack = new CustomStack<>();
    String element;
    customStack.push("1");
    customStack.push("2");
    customStack.push("3");
    element = customStack.pop();
    System.out.println(element);
    customStack.push("4");
    customStack.push("5");
    element = customStack.pop();
    System.out.println(element);
  }
}
