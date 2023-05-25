package ru.job4j.io.content;

import java.io.*;
import java.util.function.Predicate;

public abstract class FileContent {
    private final File file;
    private final Predicate<Character> predicate;

    public FileContent(File file, Predicate<Character> predicate) {
        this.file = file;
        this.predicate = predicate;
    }

    public String content() {
        StringBuilder buff = new StringBuilder();
        int data;
        try (Reader reader = new FileReader(file)) {
            while ((data = reader.read()) != -1) {
                char ch = (char) data;
                if (predicate.test(ch)) {
                    buff.append(ch);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buff.toString();
    }
}
