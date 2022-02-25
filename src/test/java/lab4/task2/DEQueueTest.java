package lab4.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DEQueueTest {

    @Test
    void toArray() {
        DEQueue<Integer> deQueue = new DEQueue<>();
        deQueue.pushFront(1);
        deQueue.pushFront(2);

        Object[] objects = deQueue.toArray();
        Integer[] integers = new Integer[objects.length];
        for (int i = 0; i < objects.length; i++) {
            integers[i] = (Integer) objects[i];
        }


        assertArrayEquals(new Integer[]{2, 1}, integers);

    }

    @Test
    void pushBack() {
        DEQueue<Integer> queue = new DEQueue<>();

        queue.pushBack(1);
        assertEquals(queue.back(), 1);

        queue.pushBack(2);
        assertEquals(queue.back(), 2);

        queue.pushBack(3);
        assertEquals(queue.back(), 3);

        queue.pushBack(4);
        assertEquals(queue.back(), 4);


        queue.popBack();
        assertEquals(queue.back(), 3);
    }

    @Test
    void pushFront() {
        DEQueue<Integer> queue = new DEQueue<>();

        queue.pushFront(1);
        assertEquals(queue.front(), 1);
        assertEquals(queue.back(), 1);

        queue.pushFront(2);
        assertEquals(queue.front(), 2);
        assertEquals(queue.back(), 1);

        queue.pushFront(3);
        queue.pushBack(9);
        assertEquals(queue.front(), 3);
        assertEquals(queue.back(), 9);

        queue.pushFront(4);
        assertEquals(queue.front(), 4);
        assertEquals(queue.back(), 9);


        queue.popFront();
        assertEquals(queue.front(), 3);
    }

    @Test
    void popBack() {
        DEQueue<Integer> queue = new DEQueue<>();

        queue.pushBack(1);
        queue.pushBack(2);
        queue.pushBack(3);
        queue.pushBack(4);
        assertEquals(queue.popBack(), 4);
        assertEquals(queue.popBack(), 3);
        assertEquals(queue.popBack(), 2);
        assertEquals(queue.popBack(), 1);
        assertNull(queue.popBack());


    }

    @Test
    void popFront() {
        DEQueue<Integer> queue = new DEQueue<>();

        queue.pushBack(1);
        queue.pushBack(2);
        queue.pushBack(3);
        queue.pushBack(4);
        assertEquals(queue.popFront(), 1);
        assertEquals(queue.popFront(), 2);
        assertEquals(queue.popFront(), 3);
        assertEquals(queue.popFront(), 4);
        assertNull(queue.popFront());
    }

    @Test
    void back() {
        DEQueue<Integer> queue = new DEQueue<>();

        assertNull(queue.back());
        queue.pushBack(1);
        assertEquals(queue.back(), 1);
        queue.pushBack(2);
        assertEquals(queue.back(), 2);
        queue.pushBack(3);
        assertEquals(queue.back(), 3);
        queue.pushBack(4);
        assertEquals(queue.back(), 4);

    }

    @Test
    void front() {
        DEQueue<Integer> queue = new DEQueue<>();

        assertNull(queue.front());
        queue.pushFront(1);
        assertEquals(queue.front(), 1);
        queue.pushFront(2);
        assertEquals(queue.front(), 2);
        queue.pushFront(3);
        assertEquals(queue.front(), 3);
        queue.pushFront(4);
        assertEquals(queue.front(), 4);
    }

    @Test
    void size() {
        DEQueue<Integer> queue = new DEQueue<>();
        assertEquals(0, queue.size());
        queue.pushBack(1);
        queue.pushFront(2);
        assertEquals(2, queue.size());
        queue.popFront();
        queue.popFront();
        assertEquals(0, queue.size());

    }

    @Test
    void clear() {
        DEQueue<Integer> queue = new DEQueue<>();
        assertEquals(0, queue.size());
        queue.pushBack(1);
        queue.pushFront(2);
        queue.clear();
        assertEquals(0, queue.size());
    }
}