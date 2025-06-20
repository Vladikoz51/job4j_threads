package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParallelIndexSearchTest {
    @Test
    void testSearchIntArray() {
        Integer[] array = {1, 2, 3, 4, 5};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(array, 3);
        assertEquals(2, search.search());
    }

    @Test
    void testSearchStringArray() {
        String[] array = {"apple", "banana", "cherry"};
        ParallelIndexSearch<String> search = new ParallelIndexSearch<>(array, "banana");
        assertEquals(1, search.search());
    }

    @Test
    void testSearchSmallArray() {
        Integer[] array = {1, 2, 3};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(array, 2);
        assertEquals(1, search.search());
    }

    @Test
    void testSearchLargeArray() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(array, 50);
        assertEquals(50, search.search());
    }

    @Test
    void testElementNotFound() {
        Integer[] array = {1, 2, 3};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(array, 4);
        assertEquals(-1, search.search());
    }
}
