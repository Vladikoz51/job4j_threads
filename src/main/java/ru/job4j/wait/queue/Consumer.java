package ru.job4j.wait.queue;

public class Consumer implements Runnable {
    private final SimpleBlockingQueue<Integer> queue;

    public Consumer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            queue.poll();
        }
    }
}