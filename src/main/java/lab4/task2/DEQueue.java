package lab4.task2;

import java.util.ArrayList;

public class DEQueue<T> {

    private ArrayList<T> payLoad = new ArrayList<>();



    public void pushBack(T item) {
        payLoad.add(
                item
        );
    }



    public void pushFront(T item) {
        payLoad.add(
                0,
                item
        );
    }


    public T popBack() {
        if (payLoad.size() != 0) {
            int index = payLoad.size() - 1;
            T lastItem = back();
            payLoad.remove(index);
            return lastItem;

        }
        return null;
    }



    public T popFront() {
        if (payLoad.size() != 0) {
            T firstItem = front();
            payLoad.remove(0);
            return firstItem;
        }
        return null;
    }



    public T back() {
        if (payLoad.size() != 0) {
            int index = payLoad.size() - 1;
            return payLoad.get(index);

        }
        return null;
    }



    public T front() {
        if (payLoad.size() != 0) {
            return payLoad.get(0);
        }
        return null;
    }



    public int size() {
        return payLoad.size();
    }



    public void clear() {
        payLoad = new ArrayList<>();
    }



    public Object[] toArray() {
        return payLoad.toArray();

    }


}
