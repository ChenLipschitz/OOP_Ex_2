package part2;

import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>, Comparable<Task<T>> {
    private final Callable<T> operation;
    private final TaskType taskType;

    //constructor method
    private Task(Callable<T> operation, TaskType taskType) {
        this.operation = operation;
        this.taskType = taskType;
    }

    /**
     * Creates a new Task object with the OTHER type- unknown type
     * @param operation Callable object
     * @param <T> generic type
     * @return a new Task object
     */
    public static <T> Task<T> createTask(Callable<T> operation) {
        return new Task<>(operation, TaskType.OTHER);
    }

    /**
     * Creates a new Task object which has a specified TaskType
     * @param operation Callable object
     * @param taskType a TaskType object
     * @param <T> generic type
     * @return a new Task object
     */
    public static <T> Task<T> createTask(Callable<T> operation, TaskType taskType) {
        return new Task<>(operation, taskType);
    }

public TaskType getTaskType(){
    return taskType.getType();
    }

    /**
     * Returns the taskType's priority
     * @return taskType's priority
     */
    private int getPriority() {
        return taskType.getPriorityValue();
    }

    /**
     * @param o an Task object
     * @return 0, if the priorities are equal
     *         1, if this object has a greater priority
     *        -1, if the given object has a greater priority
     */
    @Override
    public int compareTo(Task<T> o) {
        return Integer.compare(taskType.getPriorityValue(), o.taskType.getPriorityValue());
    }

    /**
     * Runs the Callable- call method
     * @return call method's result
     * @throws Exception if exists
     */
    @Override
    public T call() throws Exception {
        return operation.call();
    }
}

