package org.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyList {
    List<Pair> list = new ArrayList<>();
    Lock lock = new ReentrantLock();

    public boolean contains(Pair pair) {
        lock.lock();
        boolean value = list.contains(pair);
        lock.unlock();
        return value;
    }

    public void add(Pair pair) {
        lock.lock();
        list.add(pair);
        lock.unlock();
    }

    public List<Pair> getList() {
        return list;
    }
}
