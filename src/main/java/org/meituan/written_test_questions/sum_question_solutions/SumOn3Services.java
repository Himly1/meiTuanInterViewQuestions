package org.meituan.written_test_questions.sum_question_solutions;


/**
 * 编写一个Java函数，通过调用AService.get()、BService.get()、CService.get()三个接口，获取三个整数，然后将这三个整数累加，最终返回累加的值。要求：
 * 1.调用三个接口的操作需要并行执行，以提高效率；
 * 2.累加操作需要在获取三个整数的操作完成后进行，因此需要保证三个整数均已获取后才能进行累加操作；
 * 3.考虑多线程安全问题。
 */
public interface SumOn3Services {

    /**
     * 假定Service对应的共享数据同时存在读和写的情况。
     */
    interface BaseService {
        @FunctionalInterface
        interface Lock {
            void unlock();
        }

        record Rs(Long number, Lock lock) {
        }
        /**
         * 获取共享数据
         * @return 数据及读写锁。
         */
         Rs get();
    }

    interface AService extends BaseService {

    }

    interface BService extends BaseService {

    }

    interface CService extends BaseService {

    }


    /**
     * 获取总和
     * @return 总和
     */
    Long getSum();
}
