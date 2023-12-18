# The Game of Life

This repository serves as the location of the code for CEBP.

## Problem Description

This problem is a great example of how complexity can arise from simplicity. In this zero-player game, each cell can exist in one of two possible states, alive of dead. The fate of each cell is dependent on the state of its eight neighbours, which are the cells that are horizontally, vertically or diagonally adjacent.

## Rules

1. A cell is sustained by a food unit for T_full time;
2. After T_full time, the cell gets hungry;
3. When hungry, the cell dies after T_starve time if no food unit is consumed within the time frame;
4. When the cell dies, it splits in 1 to 5 random food units for other cells to consume;
5. After eating at least 10 times, a cell will multiply like this:

- if the cell is asexual, the cell multiplies through division, resulting in two hungry cells;
- if the cell is sexual, the cell multiplies through mating, where two cells make a third hungry cell.

## Potential Concurrency Issues

1. **Thread Overhead**: Managing a large number of threads may be cumbersome for the programmer, since each cell is represented by a separate thread.
2. **Race Conditions**: Because all cells are run on separate threads, they may try to access the same resources all at once, resulting in concurrency problems.
3. **Starvation**: This may occur when a thread is continuously denied access to shared resources, in this case the food units, which may prevent it from making progress. This situation could happen when a greedy thread consumes a lot of food and others get little to none.

## Proposed Architecture

- abstract class: **Cell**: the standard cell that has properties like health, hunger status, et cetera;
- class: **AsexualCell** (extends Cell): implements the reproduction behavior specific to the asexual cells (e.g. divide());
- class: **SexualCell** (extends Cell): implements the reproduction behavior specific to the sexual cells (e.g. multiply());
- class: **Food**: represents the food unit;
- class: **Environment**: manages the shared resources;
- interface: **ReproductionListener**: allows classes to listen for and handle reproduction events;
- interface: **StarvationListener**: handles starvation events.

## Solving The Concurrency Issues...

1. **Threads**: using threads enabled the usage of executing tasks in parallel, allowing different cells to operate independently and simulaneously.
2. **Synchronization**: ensured that only one thread can access a method at a time.
3. **Locks**: blocked resources when used.
4. **Semaphore**: managed access to a limited resource, restricting the number of concurrent threads in our program
5. **Concurrenct Collections**: used CopyOnWriteArrayList to ensure that our collections are safely accessed by multiple threads concurrently.
6. **Using Volatile**: ensures that changes to a variable are immediately visible to all threads.
