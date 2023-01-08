# OOP_Ex_2

### Part 1:

In the following assigment we were told to create a random number of text files and return their total number of lines.

We have implementad it in three different ways:
* Without Threads
* Using Threads
* Using ThreadPool

<b>The class methods:</b>
* createTextFiles- Returns a String array contains the created files names
* getNumOfLines- Returns the number of lines in all files

The method loops all lines in a for loop, and if there is a next line (using Scanner library) add 1 to the counter (=numOfLines), return counter.

* getNumOfLinesThreads- Returns the number of lines in all files, by using Threads 

The method 

* getNumOfLinesThreadPool- Returns the number of lines in all files, by using ExecutorService

The method creates an array of fileThread object, the size of the array is as the size of the number of files. For every file a thread is allocated.
all the threads a 

* run- 
* call- 
* cleanUp- Deletes all files
* compareTime- Clculates and prints the running time and the number of lines of each way


## UML

## RESULTS
<img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/threadPoolWins-1000.png alt="1000files">
<img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/10000%20filse.png alt="10000files">
