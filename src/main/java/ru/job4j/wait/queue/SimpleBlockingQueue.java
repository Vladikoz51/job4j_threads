package ru.job4j.wait.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int size;

    public SimpleBlockingQueue(int size) {
        if (size > 0) {
            this.size = size;
        } else {
            throw new IllegalArgumentException("Queue size must be more than 0");
        }
    }

    public synchronized void offer(T value)  {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer thread interrupted");
            }
        }
        queue.offer(value);
        notifyAll();

    }

    public synchronized T poll() throws InterruptedException {
        T rsl;
        while (isEmpty()) {
                wait();
        }
        rsl = queue.poll();
        notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized boolean isFull() {
        return queue.size() == size;
    }
}
