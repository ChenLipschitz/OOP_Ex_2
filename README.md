# OOP_Ex_2

## Part 1

In the following assigment we were told to create a random number of text files and return their total number of lines.

We have implementad it in three different ways:
* Without Threads
* Using Threads
* Using ThreadPool

<b>The class main methods:</b>
* getNumOfLines- Returns the number of lines in all files

The method loops all lines in a for loop, and if there is a next line (using Scanner library) add 1 to the counter (=numOfLines), return counter.

* getNumOfLinesThreads- Returns the number of lines in all files, by using Threads 

In this method we use multithreading. For each file we allocate a thread, which means that the number of lines is calculated for each file in a seperate thread.
Then, loop all files, if the thread has finished add the result to the total. Return total.

* getNumOfLinesThreadPool- Returns the number of lines in all files, by using ExecutorService

The method creates an array of fileThread object, the size of the array is as the size of the number of files. For every file a thread is allocated.
all the threads a 


### UML

### RESULTS
100 files:

<img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/100%20files.png alt="100files">

1000 files:

<img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/1000%20files.png alt="1000files">

10000 files:

<img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/10000%20files.png alt="10000files">

According to the results above, all the methods calculated the same number of lines in total. The main difference between them is the running time.
The threadPool method is usually, more efficient since thread pool reuses previously created threads to execute current tasks.  It offers a solution to the problem of thread cycle overhead and resource thrashing. Since the thread is already existing when the request arrives, the delay introduced by thread creation is eliminated, making the application more responsive (for more info click <a href=https://www.geeksforgeeks.org/thread-pools-java/> here </a>).


## Part 2
The goal- create new types that extend the functionality of Javas Concurrency Framework of priority in threads

## Bibliography
* <a href=https://www.geeksforgeeks.org/thread-pools-java/> geeksforgeeks </a>
