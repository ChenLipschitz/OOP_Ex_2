package part1.Tests;

import org.junit.jupiter.api.*;
import part1.Ex_2;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Tests_part1 {

    private String[] fileNames1;

    @BeforeEach
    void set_up() throws IOException {
        fileNames1 = Ex_2.createTextFiles(10, 1000, 100);
    }

    @AfterEach
    void clean_up(){
        Ex_2.cleanUp(fileNames1);
    }

    @Test
    void createTextFiles() throws IOException {
        String[] fileNames;
        fileNames = Ex_2.createTextFiles(1090, 1000, 100);
        String currName = "";
        for (int i=1; i <= fileNames.length; i++){
            currName = "file_" + i + ".txt";
            assertEquals(fileNames[i-1], currName);
        }
        assertEquals(1090, fileNames.length);
        Ex_2.cleanUp(fileNames);

        // negative number of files
        assertThrows(NegativeArraySizeException.class, ()-> {
            Ex_2.createTextFiles(-27687, 2344, 2342535);
        });
    }

    @Test
    void getNumOfLines(){
        int numOfLines = Ex_2.getNumOfLines(fileNames1);
        assertEquals(numOfLines, 10);
    }




}
