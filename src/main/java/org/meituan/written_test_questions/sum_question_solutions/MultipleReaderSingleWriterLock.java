package org.meituan.written_test_questions.sum_question_solutions;

import java.util.concurrent.Semaphore;

/**
 * 保证读写互斥，并能够支持多个线程读。
 * 之所以不适用Lock来实现是因为Lock有局限性，不适用当前的场景，因为Lock只能被所acquired的线程所release.
 * 但是在该场景中，是要实现的是一个类似事物的事务管理器，但该事物由多个不同的线程组成，因此不适用使用线程。
 * 而是使用信号量
 */
public class MultipleReaderSingleWriterLock {
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore writer = new Semaphore(1);
    private int readers = 0;

    public void readLock() {
        try {
            mutex.acquire();
            readers++;
            if (readers == 1) {
                writer.acquire();
            }
            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void readUnlock() {
        try {
            mutex.acquire();
            readers--;
            if (readers == 0) {
                writer.release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void writeLock() {
        try {
            writer.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void writeUnlock() {
        writer.release();
    }
}
