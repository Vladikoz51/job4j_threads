package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> {
    private final T[] array;
    private final T target;

    public ParallelIndexSearch(T[] array, T target) {
        this.array = array;
        this.target = target;
    }

    public int search() {
        if (array.length <= 10) {
            return linearSearch();
        } else {
            return parallelSearch();
        }
    }

    private int linearSearch() {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                rsl = i;
            }
        }
        return rsl;
    }

    private int parallelSearch() {
        ForkJoinPool pool = new ForkJoinPool();
        SearchTask task = new SearchTask(0, array.length - 1);
        return pool.invoke(task);
    }

    private class SearchTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        public SearchTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (start == end) {
                return array[start].equals(target) ? start : -1;
            } else {
                int mid = (start + end) / 2;
                SearchTask leftTask = new SearchTask(start, mid);
                SearchTask rightTask = new SearchTask(mid + 1, end);
                leftTask.fork();
                rightTask.fork();

                int leftResult = leftTask.join();
                int rightResult = rightTask.join();
                return leftResult != -1 ? leftResult : rightResult;
            }
        }
    }
}