package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int idx, int length) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < length; i++) {
                rowSum += data[idx][i];
                colSum += data[i][idx];
            }
            return new Sums(rowSum, colSum);
        });
    }

    public static Sums[] sum(int[][] matrix) {
        int rowCount = matrix.length;
        int rowSum;
        int colSum;
        Sums[] sums = new Sums[rowCount];
            for (int i = 0; i < rowCount; i++) {
                rowSum = 0;
                colSum = 0;
                for (int j = 0; j < rowCount; j++) {
                    rowSum += matrix[i][j];
                    colSum += matrix[j][i];
                }
                sums[i] = new Sums(rowSum, colSum);
            }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int k = 0; k <= n / 2; k++) {
            futures.put(k, getTask(matrix, k, n));
            if (k < n / 2) {
                futures.put(n - 1 - k, getTask(matrix, n - 1 - k, n));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }
}