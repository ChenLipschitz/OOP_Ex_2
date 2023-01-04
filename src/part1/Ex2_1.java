package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex2_1 {
    public static void main(String[] args) throws IOException {
//        String[] fileNames = createTextFiles(100, 1000, 100);
//        for (String fileName : fileNames) {
//            System.out.println(fileName);
//            cleanUp(fileNames);
//        }
//        System.out.println("Number of lines: " + getNumOfLines(fileNames));
//        System.out.println("Number of lines using threads: " + new part1.Ex2_1().getNumOfLinesThreads(fileNames));
//        System.out.println("Number of lines using thread pool: " + new part1.Ex2_1().getNumOfLinesThreadPool(fileNames));
//        new part1.Ex2_1().compareTime();
    }

    /**
     * @param n     number of files to generate
     * @param seed  seed for random number generator
     * @param bound bound for random number generator
     */
    public static String[] createTextFiles(int n, int seed, int bound) throws IOException {
        String[] fileNames = new String[n];
        Random random = new Random(seed);
        for (int i = 0; i < n; i++) {
            fileNames[i] = "file" + i + ".txt";
            try (PrintWriter writer = new PrintWriter(fileNames[i])) {
                //write random of rows with at least 10 characters in each row
                for (int j = 0; j < random.nextInt(bound) + 10; j++) {
                    writer.println(random.nextInt());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        return fileNames;
    }

    /**
     * Reads all files and count the number of rows in each file
     *
     * @param fileNames array (string type) of file names
     * @return the number of lines in all files
     */
    public static int getNumOfLines(String[] fileNames) {
        int numOfLines = 0;
        for (String fileName : fileNames) {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNextLine()) {
                    scanner.nextLine();
                    numOfLines++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return numOfLines;
    }

    /**
     * Counts the number of lines in a file using threads
     *
     * @param fileNames array (string type) of file names
     * @return the number of lines in all files
     */
    public int getNumOfLinesThreads(String[] fileNames) {
        //count the number of lines in the files using threads
        class thread extends Thread {
            int numOfLines = 0;
            String fileName = "";

            public thread(String fileName) {
                this.fileName = fileName;
            }

            @Override
            public void run() {
                try (Scanner scanner = new Scanner(new File(fileName))) {
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                        numOfLines++;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        thread[] threads = new thread[fileNames.length];//create threads array with the size of the files array
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new thread(fileNames[i]);//create a thread for each file
            threads[i].start();//start the thread
        }
        int count = 0;//count the number of lines
        for (thread thread : threads) {
            try {
                thread.join();//wait for the thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count += thread.numOfLines;//add the number of lines in the file to the total
        }
        return count;//return the total count
    }

    /**
     * this method is the same as the previous one but uses ExecutorService instead of threads
     *
     * @param fileNames array of file names
     * @return the number of lines in the files
     */
    public int getNumOfLinesThreadPool(String[] fileNames) {
        //count the number of lines in the files using thread pool
        AtomicInteger count = new AtomicInteger(0);//counter for the number of lines
        //create a thread pool with the size of the files array
        ExecutorService executorService = Executors.newFixedThreadPool(fileNames.length);//It is very inefficient, it is better to make a much smaller number of threads
        for (String fileName : fileNames) {
            executorService.execute(() -> {
                try (Scanner scanner = new Scanner(new File(fileName))) {
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                        count.getAndIncrement();//add 1 to the count
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        //shut down the thread pool after all tasks have been submitted
        executorService.shutdown();
        //block the current thread until all tasks have completed execution
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count.get();
    }

    /**
     * Compares the performance of the methods
     */
    public void compareTime() throws IOException {
        String[] fileNames = createTextFiles(1000, 1000, 10000);
        long start = System.currentTimeMillis();
        System.out.println(getNumOfLinesThreads(fileNames));
        long end = System.currentTimeMillis();
        System.out.println("Time using threads: " + (end - start) + "ms");
        start = System.currentTimeMillis();
        System.out.println(getNumOfLinesThreadPool(fileNames));
        end = System.currentTimeMillis();
        System.out.println("Time using thread poll: " + (end - start) + "ms");
        start = System.currentTimeMillis();
        System.out.println(getNumOfLines(fileNames));
        end = System.currentTimeMillis();
        System.out.println("Time without threads: " + (end - start) + "ms");

    }

    /**
     * @param fileNames array of file names
     *                  This method is used to delete the files
     */
    public static void cleanUp(String[] fileNames) {
        for (String fileName : fileNames) {
            java.io.File file = new java.io.File(fileName);
            file.delete();
        }
    }


    public static class Call implements Callable<Integer> {
        String fileName;

        public Call(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public Integer call() throws Exception {
            int numOfLines = 0;
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNextLine()) {
                    scanner.nextLine();
                    numOfLines++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return numOfLines;
        }
    }
}