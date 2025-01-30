package ru.job4j.wait.queue;

import java.util.Random;

public class Producer implements Runnable {
    private final SimpleBlockingQueue<Integer> queue;

    public Producer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();
        queue.offer(random.nextInt());
    }
}