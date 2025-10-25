1. Use CompletableFuture.supplyAsync() to fetch "user data" (simulated sleep + random string) and "order data" in parallel, then thenCombine() to merge them into a report printed asynchronously.
2. Chain multiple futures using thenApply, thenCompose, and thenAccept.
3. Create a e-commerce pipeline to fetch price, shippings costs and discount using `allOf()`





