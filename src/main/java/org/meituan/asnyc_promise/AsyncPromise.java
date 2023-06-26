package org.meituan.asnyc_promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 基于 Async 和 Promise 的思想对于并发的抽象层。
 * 简而言之：
 *        告诉我你要做什么，我会在单独的线程中执行该函数，并立即返回一个 Promise 对象，通过该 Promise 对象可以使用 await 去等待结果.
 */
public interface AsyncPromise {
    interface Async<V> extends Callable<V> {

    }

    interface Promise<V> {
        V await() throws InterruptedException, ExecutionException;
    }

    <V> Promise<V> promise(Async<V> func);

    void releasePool();
}
