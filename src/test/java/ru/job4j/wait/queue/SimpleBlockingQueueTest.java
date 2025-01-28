package ru.job4j.wait.queue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenProducingAndConsumingElements() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(100);
        Thread consumer = new Thread(new Consumer(queue));
        Thread producer = new Thread(new Producer(queue));
        producer.start();
        producer.join();
        assertThat(queue.isFull()).isTrue();
        assertThat(queue.isEmpty()).isFalse();
        consumer.start();
        consumer.join();
        assertThat(queue.isFull()).isFalse();
        assertThat(queue.isEmpty()).isTrue();
    }
}