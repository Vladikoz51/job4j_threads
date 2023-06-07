package ru.job4j.cash;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenNotEnoughMoney() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 3600));
        storage.add(new Account(2, 480));
        assertThat(storage.transfer(1, 2, 5000)).isFalse();
    }

    @Test
    void whenAccountNotExists() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 3600));
        storage.add(new Account(2, 480));
        assertThat(storage.transfer(1, 3, 600)).isFalse();
    }

    @Test
    void whenFromEqualsTo() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 3600));
        assertThat(storage.transfer(1, 1, 600)).isFalse();
    }

    @Test
    void whenAmountIsLessThanZero() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 3600));
        storage.add(new Account(2, 480));
        assertThat(storage.transfer(1, 2, -600)).isFalse();
    }
}