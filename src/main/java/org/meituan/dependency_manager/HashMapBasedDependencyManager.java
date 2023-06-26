package org.meituan.dependency_manager;

import org.meituan.asnyc_promise.AsyncPromise;
import org.meituan.asnyc_promise.FeatureBasedAsyncPromise;
import org.meituan.written_test_questions.student_filter.SolutionBasedOnHashMap;
import org.meituan.written_test_questions.student_filter.StudentFilter;
import org.meituan.written_test_questions.subtract_2_linked_list.SolutionBasedOnSingleList;
import org.meituan.written_test_questions.subtract_2_linked_list.Subtract2LinkedList;
import org.meituan.written_test_questions.sum_question_solutions.SumOn3Services;
import org.meituan.written_test_questions.sum_question_solutions.SolutionOfSumQuestionWithStaticNumber;

import java.util.HashMap;

public class HashMapBasedDependencyManager implements DependencyManager {
    private final HashMap<Class<?>, Object> typeAndDependencyMapping = new HashMap<>();

    {
        AsyncPromise asyncPromise = new FeatureBasedAsyncPromise(8L);
        SumOn3Services sumQuestion = new SolutionOfSumQuestionWithStaticNumber(asyncPromise);
        Subtract2LinkedList subtract2LinkedList = new SolutionBasedOnSingleList();
        StudentFilter studentFilter = new SolutionBasedOnHashMap(asyncPromise);
        typeAndDependencyMapping.put(AsyncPromise.class,asyncPromise);
        typeAndDependencyMapping.put(SumOn3Services.class, sumQuestion);
        typeAndDependencyMapping.put(Subtract2LinkedList.class, subtract2LinkedList);
        typeAndDependencyMapping.put(StudentFilter.class, studentFilter);
    }

    @Override
    public <T> T getDependencyByType(Class<T> clz) {
        T dep = (T) typeAndDependencyMapping.get(clz);
        if (dep == null) {
            throw new RuntimeException("No such dependency exists. The type is: " + clz.getName());
        }

        return dep;
    }
}
