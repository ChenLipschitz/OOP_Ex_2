import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class MyFutureTask<V> extends FutureTask<V> implements Comparable<MyFutureTask> {

    private int priority = 0;

    public MyFutureTask(Callable<V> callable, int priority) {
        super(callable);
        this.priority = priority;
    }

    /**
     * @return the objects priority
     */
    public int getPriority(){
        return this.priority;
    }


    /**
     * Override method that cast the Task class to be
     * a comparable class
     * @param other MyFutureTask object
     * @return 0, if the priorities are equal
     *         1, if this object has a greater priority
     *        -1, if the given object has a greater priority
     */
    @Override
    public int compareTo(MyFutureTask other) {
        int diff = this.getPriority() - other.getPriority();
        return Integer.compare(0, diff);
    }
}
