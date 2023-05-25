package ru.job4j.io;

import ru.job4j.io.content.FileContent;
import java.io.File;

public class ParseFile {
    private final File file;

    private final FileContent fileContent;

    public ParseFile(File file, FileContent fileContent) {
        this.file = file;
        this.fileContent = fileContent;
    }

    public synchronized String getContent() {
        return fileContent.content();
    }
}