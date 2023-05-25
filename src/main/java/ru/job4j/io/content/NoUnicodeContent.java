package ru.job4j.io.content;

import java.io.File;

public class NoUnicodeContent extends FileContent {
    public NoUnicodeContent(File file) {
        super(file, (ch) -> (int) ch < 0x80);
    }
}
