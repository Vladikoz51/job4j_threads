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
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable job = tasks.poll();
                        job.run();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void start() {
        threads.forEach(Thread::start);
    }

    public void shutdown() {
        threads.forEach((Thread::interrupt));
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(10);
        for (int i = 1; i <= 100; i++) {
            int taskNum = i;
            pool.work(() -> {
                for (int j = 0; j < 10; j++) {
                    System.out.printf("%s works on task %s%n", Thread.currentThread().getName(), taskNum);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        pool.start();
        Thread.sleep(50000);
        pool.shutdown();
    }
}