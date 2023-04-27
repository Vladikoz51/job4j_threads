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

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("tmp.txt")) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            long actualSpeed;
            while (true) {
                Instant start = Instant.now();
                bytesRead = in.read(dataBuffer, 0, speed);
                Instant finish = Instant.now();
                if (bytesRead == -1) {
                    break;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                actualSpeed = Duration.between(start, finish).toMillis();
                if (actualSpeed < speed) {
                    Thread.sleep(1000 - actualSpeed);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        UrlValidator urlValidator = new UrlValidator();

        if (!urlValidator.isValid(url) || speed <= 0) {
            throw new IllegalArgumentException();
        }

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}