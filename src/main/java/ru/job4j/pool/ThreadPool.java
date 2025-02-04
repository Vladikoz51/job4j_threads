package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import ru.job4j.wait.queue.SimpleBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public ThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    public void work(Runnable job) {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            tasks.offer(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Task " + finalI + "works.");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public void shutdown() {
        threads.stream()
                .filter((thread) -> thread.getState().compareTo(Thread.State.RUNNABLE) == 0)
                .forEach((Thread::interrupt));
    }
}