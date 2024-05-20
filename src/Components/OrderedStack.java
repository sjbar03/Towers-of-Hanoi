package Components;

import java.util.Stack;

public class OrderedStack<T extends Comparable<T>> extends Stack<T> {

    @Override
    public boolean add(T e) {
        if (e.compareTo(peek()) >= 0) {
            return false;
        }
        return super.add(e);
    }


}
