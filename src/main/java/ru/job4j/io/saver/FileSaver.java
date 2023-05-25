package ru.job4j.io.saver;

import java.io.*;

public class FileSaver implements Saver {

    private final File file;

    public FileSaver(File file) {
        this.file = file;
    }

    @Override
    public synchronized void saveContent(String content) {
        try (Writer o = new FileWriter(file)) {
            o.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}