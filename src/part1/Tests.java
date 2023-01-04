package part1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

class Tests {

    static String[] flList;

    @BeforeAll
    static void start() throws IOException {
        flList = Ex2_1.createTextFiles(10000, 100, 100);

        Utilities.timeCounter("getNumOfLines", flList);
        Utilities.timeCounter("getNumOfLinesThreads", flList);
        Utilities.timeCounter("getNumOfLinesThreadPool", flList);
//        System.out.println(Arrays.toString(flList));
    }

    @AfterAll
    static void deleteFiles(){
        Arrays.stream(flList).forEach(f -> new File(f).deleteOnExit());
    }

    @org.junit.jupiter.api.Test
    void createTextFiles() {
    }

    @org.junit.jupiter.api.Test
    void getNumOfLines() {
    }

    @org.junit.jupiter.api.Test
    void getNumOfLinesThreads() {
    }

    @org.junit.jupiter.api.Test
    void getNumOfLinesThreadPool() {
    }
}