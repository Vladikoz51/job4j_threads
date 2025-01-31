package ru.job4j.cache;

public record Base(int id, String name, int version) {

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
}
