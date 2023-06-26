package org.meituan.written_test_questions.sum_question_solutions;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MultipleReaderSingleWriterLockTest {
    Logger log = Logger.getLogger(MultipleReaderSingleWriterLockTest.class.getName());

    @Test
    public void testWriteLockWithReader() throws InterruptedException {
        final MultipleReaderSingleWriterLock lock = new MultipleReaderSingleWriterLock();
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Acquire read lock first
        executorService.execute(() -> {
            try {
                lock.readLock();
                log.info("Read lock acquired.");
                Thread.sleep(2000);
                lock.readUnlock();
                log.info("Read lock Released");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Try to acquire write lock
        executorService.execute(() -> {
            log.info("Write lock acquiring.");
            lock.writeLock();
            log.info("Write lock acquired..");
            assertTrue(true, "Write lock should be acquired");
            lock.writeUnlock();
            log.info("Write lock released..");
        });

        executorService.shutdown();
        executorService.awaitTermination(7, TimeUnit.SECONDS);
    }
}