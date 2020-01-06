package com.rubiconwater.techinterview;

public class SimpleStack<T> {

  private SimpleQueue<T> simpleQueue;
  private SimpleQueue<T> simpleQueueBackup;

  public SimpleStack(SimpleQueue<T> simpleQueueBackup) {
    this.simpleQueueBackup = simpleQueueBackup;
  }

  void push(T t) {
    simpleQueue.queue(t);
  }

  T pop() {
    T t = null;
    for (int i = 0; i < simpleQueue.size(); i++) {
      t = simpleQueue.dequeue();
      if (i != (simpleQueue.size() - 1)) {
        simpleQueueBackup.queue(t);
      }
    }
    simpleQueue = simpleQueueBackup;
    return t;
  }
}
