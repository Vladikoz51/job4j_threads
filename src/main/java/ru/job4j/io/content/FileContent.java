package ru.job4j.io.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

public abstract class FileContent {
    private final File file;
    private final Predicate<Character> predicate;

    public FileContent(File file, Predicate<Character> predicate) {
        this.file = file;
        this.predicate = predicate;
    }

    public String content() {
        String output = "";
        try (BufferedReader i = new BufferedReader(new FileReader(file))) {

            output = i.lines()
                    .map(str -> str.concat(System.lineSeparator()))
                    .flatMap(str -> str.chars().mapToObj(ch -> (char) ch))
                    .filter(predicate)
                    .map(Object::toString)
                    .reduce("", String::concat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
