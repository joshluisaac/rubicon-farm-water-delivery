package com.rubiconwater.techinterview;


import java.util.Queue;

public class SimpleStack<T> {

    private SimpleQueue<T> simpleQueue;
    private SimpleQueue<T> simpleQueueBackup;
    //private Queue<T> queue;


    public SimpleStack(SimpleQueue<T> simpleQueue2) {
        this.simpleQueueBackup = simpleQueue2;
    }



    void push(T t) {
        simpleQueue.queue(t);
    }

    T pop() {
        T t = null;
        for (int i = 0; i < simpleQueue.size(); i++) {
            t = simpleQueue.dequeue();
            if (i != (simpleQueue.size() - 1) ) {
                simpleQueueBackup.queue(t);
            }
        }
        simpleQueue = simpleQueueBackup;
        return t;
    }
}
