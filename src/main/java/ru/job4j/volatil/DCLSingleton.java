package ru.job4j.volatil;

public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            DCLSingleton dclSingleton = DCLSingleton.instOf();
            System.out.println(dclSingleton);
        });
        thread.start();
        System.out.println(instOf());
    }

}
