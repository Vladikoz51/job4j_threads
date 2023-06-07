package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(int id) {
        boolean rsl = false;
        if (accounts.containsKey(id)) {
            accounts.remove(id);
            rsl = true;
        }
        return rsl;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        if (from.isPresent()
                && to.isPresent()
                && amount > 0
                && from.get().amount() >= amount
                && !from.equals(to)) {
            Account newFrom = new Account(fromId, from.get().amount() - amount);
            Account newTo = new Account(toId, to.get().amount() + amount);
            update(newFrom);
            update(newTo);
            rsl = true;
        }
        return rsl;
    }
}