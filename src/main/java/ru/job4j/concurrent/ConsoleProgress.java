package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private final char[] process = {'-', '\\', '|', '/'};

    @Override
    public void run() {
        try {
            for (int idx = 0; !Thread.currentThread().isInterrupted(); idx++) {
                if (idx == process.length) {
                    idx = 0;
                }
                System.out.printf("Loading... %s.\r", process[idx]);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Thread progress = new Thread(new ConsoleProgress());
            progress.start();
            Thread.sleep(5000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
