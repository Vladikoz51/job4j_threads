package ru.job4j.CAS;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int cnt;
        do {
            cnt = get();
        } while (!count.compareAndSet(cnt, cnt + 1));
    }

    public int get() {
        return count.get();
    }
}