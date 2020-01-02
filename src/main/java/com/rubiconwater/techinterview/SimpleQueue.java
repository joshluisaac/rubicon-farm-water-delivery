package com.rubiconwater.techinterview;

public interface SimpleQueue<T> {


    void queue(T t);

    T dequeue();

    int size();


}
