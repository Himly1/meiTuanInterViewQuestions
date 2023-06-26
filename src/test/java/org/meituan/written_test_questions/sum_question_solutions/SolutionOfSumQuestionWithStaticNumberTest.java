package org.meituan.written_test_questions.sum_question_solutions;

import org.junit.jupiter.api.Test;
import org.meituan.asnyc_promise.AsyncPromise;
import org.meituan.asnyc_promise.FeatureBasedAsyncPromise;
class SolutionOfSumQuestionWithStaticNumberTest {
    AsyncPromise asyncPromise = new FeatureBasedAsyncPromise(4L);
    SolutionOfSumQuestionWithStaticNumber solution = new SolutionOfSumQuestionWithStaticNumber(asyncPromise);
    @Test
    void getSum() {
        Long sum = solution.getSum();
        assert sum.equals(Services.numberRepresentSharedDataOfServiceA + Services.numberRepresentSharedDataOfServiceB + Services.numberRepresentSharedDataOfServiceC);
    }
}