package org.meituan.written_test_questions.sum_question_solutions;

/**
 * 表示其中的三个服务,虽然说该类中，似乎继承是最优解，但处于个人对于继承的一些偏见，拒绝使用继承。
 */
public class Services {
    public static Long numberRepresentSharedDataOfServiceA = 234121L;
    public static Long numberRepresentSharedDataOfServiceB = 11213546L;

    public static Long numberRepresentSharedDataOfServiceC = 45583256L;

    static class StaticAService implements SumOn3Services.AService {
        private final MultipleReaderSingleWriterLock lock = new MultipleReaderSingleWriterLock();

        private final Lock unlock = lock::readUnlock;
        @Override
        public Rs get() {
            lock.readLock();
            return new Rs(numberRepresentSharedDataOfServiceA, unlock);
        }
    }

    static class StaticBService implements SumOn3Services.BService {
        private final MultipleReaderSingleWriterLock lock = new MultipleReaderSingleWriterLock();

        private final Lock unlock = lock::readUnlock;
        @Override
        public Rs get() {
            lock.readLock();
            return new Rs(numberRepresentSharedDataOfServiceB, unlock);
        }
    }

    static class StaticCService implements SumOn3Services.CService {
        private final MultipleReaderSingleWriterLock lock = new MultipleReaderSingleWriterLock();

        private final Lock unlock = lock::readUnlock;
        @Override
        public Rs get() {
            lock.readLock();
            return new Rs(numberRepresentSharedDataOfServiceC, unlock);
        }
    }
}
