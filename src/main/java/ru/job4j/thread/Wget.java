package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String destPath;

    public Wget(String url, int speed, String destPath) {
        this.url = url;
        this.speed = speed;
        this.destPath = destPath;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destPath)) {
            int stepSize = Math.min(speed, 100);
            byte[] dataBuffer = new byte[stepSize];
            int bytesRead;
            int totalRead = 0;
            long actualTime;
            Instant finish;
            Instant start = Instant.now();
            while ((bytesRead = in.read(dataBuffer, 0, stepSize)) != -1) {
                totalRead += bytesRead;
                if (speed <= totalRead) {
                    finish = Instant.now();
                    actualTime = Duration.between(start, finish).toMillis();
                    if (actualTime < 1000) {
                        Thread.sleep(1000 - actualTime);
                    }
                    totalRead = 0;
                    start = Instant.now();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException();
        }

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String dest = args[2];
        UrlValidator urlValidator = new UrlValidator();

        if (!urlValidator.isValid(url) || speed <= 0) {
            throw new IllegalArgumentException();
        }

        Thread wget = new Thread(new Wget(url, speed, dest));
        wget.start();
        wget.join();
    }
}