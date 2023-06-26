package org.meituan.asnyc_promise;

import java.util.concurrent.*;

public class FeatureBasedAsyncPromise implements AsyncPromise {
    private final ThreadPool threadPool;

    public record Promise<T>(Future<T> future) implements AsyncPromise.Promise<T> {
        public T await() throws InterruptedException, ExecutionException {
            return future.get();
        }
    }

    private static class ThreadPool {
        private final ExecutorService executorService;

        public ThreadPool(int poolSize) {
            this.executorService = Executors.newFixedThreadPool(poolSize);
        }

        public <T> Promise<T> promise(Callable<T> task) {

            Future<T> future = executorService.submit(task);
            return new Promise<>(future);
        }

        public void shutdown() {
            executorService.shutdown();
        }
    }

    public FeatureBasedAsyncPromise(Long poolSize) {
        this.threadPool = new ThreadPool(6);
    }

    @Override
    public <V> AsyncPromise.Promise<V> promise(Async<V> func) {
        return this.threadPool.promise(func);
    }

    public void releasePool() {
        this.threadPool.shutdown();
    }
}
