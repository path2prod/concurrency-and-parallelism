1. Use `PriorityBlockingQueue` to simulate tasks with priorities.
2. Use `ConcurrentHashMap` to count word occurrences in parallel.
3. Ingest items via producers into a `SynchronousQueue`, process in stages with `CompletableFutures` (filter → map → aggregate)
4. Use a `CountDownLatch` to wait for several threads to complete.
5. Simulate a pipeline using `CyclicBarrier` where painter depends on plasters and these on electricians 
6. Implement a simple parallel sum for a IntStream of 10_000 elements using ` ForkJoinPool`
7. Simulate 10 users trying to access a shared service but only 3 threads can access it at the same time using `Semaphore`.



