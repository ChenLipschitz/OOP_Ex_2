import java.util.concurrent.*;

public class CustomExecutor <T> extends ThreadPoolExecutor{
    private int[] arrPriorities = {0, 0, 0};
    //index- represents the priority(-1),
    // arrPriorities[i]- represents the number of tasks with i+1 priority
    private static final int MIN_NUM_OF_THREADS = Runtime.getRuntime().availableProcessors()/2;
    private static final int MAX_NUM_OF_THREADS = Runtime.getRuntime().availableProcessors()-1;

    //constructor
    public CustomExecutor() {
        super(MIN_NUM_OF_THREADS,
                MAX_NUM_OF_THREADS,
                300,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>());
    }

    /**
     *
     * @param task
     * @param <T>
     * @return
     */
    protected <T> MyFutureTask<T> newTask(Task task) {
        return new MyFutureTask<T>(task.getCall(), task.getPriority());
    }

    /**
     *
     * @param task
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Task<T> task) {
        if (task == null){
            throw new NullPointerException();
        }
//        RunnableFuture<T> futureTask = newTaskFor(task);
        MyFutureTask<T> futureTask = newTask(task);
        if (task.getPriority()>0)
            arrPriorities[task.getPriority()-1]++;
        execute(futureTask);
        return futureTask;

    }

    /**
     *
     * @param callable
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable){
        Task<T> task = Task.createTask(callable);
        return submit(task);
    }

    /**
     *
     * @param callable
     * @param type TaskType object
     * @param <T> generic type
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        Task<T> task = Task.createTask(callable, type);
        return submit(task);
    }


    /**
     * Returns the max priority in queue in O(1) complexity
     * @return 1 or 2 or 3, the max priority in the queue, 1 is the highest
     */
    public int getCurrentMax(){
        try {
            if (arrPriorities[0] > 0)
                return 1;
            if (arrPriorities[1] > 0)
                return 2;
            if (arrPriorities[2] > 0)
                return 3;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     *
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (getCurrentMax() > 0){
            arrPriorities[getCurrentMax()-1]--;
        }
    }

    /**
     *
     * @throws InterruptedException
     */
    public void gracefullyTerminate() throws InterruptedException {
         try{
             awaitTermination(300, TimeUnit.MILLISECONDS);
             super.shutdown();
         }catch (InterruptedException e){
             System.out.println("Got InterruptedException: "+e.getMessage());
         }
    }
}
