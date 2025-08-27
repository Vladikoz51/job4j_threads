package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class RolColSumTest {

    @Test
    void testSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(6, RolColSum.sum(matrix)[0].getRowSum());
        assertEquals(12, RolColSum.sum(matrix)[0].getColSum());
    }

    @Test
    public void testAsyncSumSecondElement() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(15, RolColSum.asyncSum(matrix)[1].getRowSum());
        assertEquals(15, RolColSum.sum(matrix)[1].getColSum());
    }

    @Test
    public void testAsyncSumFourthElement() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        assertEquals(58, RolColSum.asyncSum(matrix)[3].getRowSum());
        assertEquals(40, RolColSum.sum(matrix)[3].getColSum());
    }

    @Test
    public void testAsyncSumThirdElement() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        assertEquals(42, RolColSum.asyncSum(matrix)[2].getRowSum());
        assertEquals(36, RolColSum.sum(matrix)[2].getColSum());
    }
}