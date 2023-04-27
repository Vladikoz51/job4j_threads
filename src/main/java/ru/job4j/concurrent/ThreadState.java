package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {

                }
        );
        Thread second = new Thread(
                () -> {

                }
        );
        System.out.printf("%s: %s" + System.lineSeparator(), first.getName(), first.getState());
        System.out.printf("%s: %s"  + System.lineSeparator(), second.getName(), second.getState());
        first.start();
        second.start();
        do {
            System.out.printf("%s: %s"  + System.lineSeparator(), first.getName(), first.getState());
            System.out.printf("%s: %s"  + System.lineSeparator(), second.getName(), second.getState());
        } while (first.getState() != Thread.State.TERMINATED && second.getState() != Thread.State.TERMINATED);
        System.out.printf("%s: %s"  + System.lineSeparator(), first.getName(), first.getState());
        System.out.printf("%s: %s"  + System.lineSeparator(), second.getName(), second.getState());

        System.out.println("Работа завершена.");
    }
}