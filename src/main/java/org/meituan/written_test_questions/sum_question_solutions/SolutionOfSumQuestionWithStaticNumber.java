package org.meituan.written_test_questions.sum_question_solutions;

import org.meituan.asnyc_promise.AsyncPromise;

import java.util.logging.Logger;

/**
 * 前提假设：
 *         该实现中将假定这三个Service中所提供的数据是共享数据，即对共享的数据同时存在读和写的操作。
 *         在该解决方案中，假定不存在集群部署的情况,因为集群部署的情况，需要用到分布式锁，因此并不在该解决方案的考虑范围内。
 *         这三个操作，应该保证在同一个”事物“中完成，即要么全部失败，要么全部成功。因此，在没有完成计算前，对于这三个service对应的共享数据，应该加锁，禁止在”事物“未提交前进行修改操作。
 *
 */
public class SolutionOfSumQuestionWithStaticNumber implements SumOn3Services {
    private final Logger logger = Logger.getLogger(SolutionOfSumQuestionWithStaticNumber.class.getName());

    private final AsyncPromise asyncPromise;

    static class UnknownError extends RuntimeException {
        UnknownError(String message, Throwable err) {
            super(message, err);
        }
    }

    private final SumOn3Services.AService aService;
    private final SumOn3Services.BService bService;
    private final SumOn3Services.CService cService;

    public SolutionOfSumQuestionWithStaticNumber(AsyncPromise asyncPromise) {
        this.aService = new Services.StaticAService();
        this.bService = new Services.StaticBService();
        this.cService = new Services.StaticCService();
        this.asyncPromise = asyncPromise;
    }

    @Override
    public Long getSum() {
        AsyncPromise.Promise<BaseService.Rs> promiseOfA = asyncPromise.promise(this.aService::get);
        AsyncPromise.Promise<BaseService.Rs> promiseOfB = asyncPromise.promise(this.bService::get);
        AsyncPromise.Promise<BaseService.Rs> promiseOfC = asyncPromise.promise(this.cService::get);
        BaseService.Rs rsOfA = null;
        BaseService.Rs rsOfB = null;
        BaseService.Rs rsOfC = null;
        try {
            rsOfA = promiseOfA.await();
            rsOfB = promiseOfB.await();
            rsOfC = promiseOfC.await();
            return rsOfA.number() + rsOfB.number() + rsOfC.number();
        }catch (Exception ex) {
            logger.severe("Failed to sum the numbers. The exception is: " +  ex.getMessage());
            throw new UnknownError("Failed to sum the numbers", ex);
        }finally {
            BaseService.Rs[] shouldUnlock = {rsOfA, rsOfB, rsOfC};
            for (BaseService.Rs rs: shouldUnlock) {
                if (rs == null) {
                    continue;
                }
                rs.lock().unlock();
            }
            asyncPromise.releasePool();
        }
    }
}
