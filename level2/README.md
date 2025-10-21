1. Implement a counter class incremented by 100000 threads in a loop (10 times each). Run it without synchronization to show the race condition.
2. Fix exercise 1 using `synchronized`.
3. Fix exercise 1 using `AtomicInteger`.
4. Create a FixedThreadPool with 3 threads and submit 10 logging tasks.
5. Create a FixedThreadPool with 3 threads to submit 10 computing tasks calculating the sum of squares from 1 to 10. Collect results using get() on Futures and print the total sum.
6. Implement a producer-consumer pattern using wait() and notify().
7. Do exercise 6 using `BlockingQueue`.
8. Create a livelock, unblock using backoff. Use Reentrant
9. Create an ExecutorService with 3 threads running infinite loops. From main, submit 3 finite tasks, and shutdown everthing greatfully
10. Use AtomicReference to track a "processed" flag in a task queue. Threads attempt to process items only if not already marked, using compareAndSet().






