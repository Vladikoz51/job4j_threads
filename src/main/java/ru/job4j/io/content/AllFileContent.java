package ru.job4j.io.content;

import java.io.File;

public class AllFileContent extends FileContent {
    public AllFileContent(File file) {
        super(file, (ch) -> true);
    }
}
