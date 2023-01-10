import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;

public class CustomExecutor <T> extends ThreadPoolExecutor{

    private HashMap<Integer, Integer> priorities;   //key- priority number, value- the number of tasks with that priority
    private static final int MIN_NUM_OF_THREADS = Runtime.getRuntime().availableProcessors()/2;
    private static final int MAX_NUM_OF_THREADS = Runtime.getRuntime().availableProcessors()-1;


    public CustomExecutor() {
        super(MIN_NUM_OF_THREADS,
                MAX_NUM_OF_THREADS,
                3000,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(MIN_NUM_OF_THREADS,
                        (task1, task2) -> ((Task)task1).compareTo((Task) task2)));
        setCounts();
    }

    /**
     * initiates the hashmap
      */
    private void setCounts(){
        priorities.put(1,0);
        priorities.put(2,0);
        priorities.put(3,0);
    }



    public <T> Future<T> submit(Task<T> task) {
        FutureTask futureTask = new FutureTask(task);
        int currPriority = priorities.get(task.getPriority());
        priorities.put(task.getPriority(), currPriority+1);
        return (Future<T>) futureTask;
    }


    public <T> Future<T> submit(Callable<T> callable){
        Task<T> task = Task.createTask(callable);
        return submit(task);
    }

    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        Task<T> task = Task.createTask(callable, type);
        return submit(task);
    }


    /**
     * Returns the max priority in queue in O(1) complexity
     * @return 1 or 2 or 3, the max priority in the queue, 1 is the highest
     */
    public int getCurrentMax(){
        if (priorities.get(1) > 0)
            return 1;
        if (priorities.get(2) > 0)
            return 2;
        if (priorities.get(3) > 0)
            return 3;
        return 0;
    }


}
