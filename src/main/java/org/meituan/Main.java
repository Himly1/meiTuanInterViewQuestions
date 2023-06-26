package org.meituan;


import org.meituan.dependency_manager.DependencyManager;
import org.meituan.dependency_manager.HashMapBasedDependencyManager;
import org.meituan.written_test_questions.student_filter.StudentFilter;
import org.meituan.written_test_questions.subtract_2_linked_list.Subtract2LinkedList;
import org.meituan.written_test_questions.sum_question_solutions.SumOn3Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final DependencyManager dependencyManager = new HashMapBasedDependencyManager();

    public static void runTheSolutionOfSumOutputOf3Services() {
        SumOn3Services sumQuestion = dependencyManager.getDependencyByType(SumOn3Services.class);
        Long sum = sumQuestion.getSum();
        log.info("第一题的结果为: " + sum + "详细测试请移步单元测试.");
    }

    public static void runTheSolutionOfSub2LinkedListTheLeftNumberIsZero() {
        Subtract2LinkedList subtract2LinkedList = dependencyManager.getDependencyByType(Subtract2LinkedList.class);
        Subtract2LinkedList.LinkedList[] i1Andi2 = subtract2LinkedList.create2List(0L,456L);
        Subtract2LinkedList.Results output = subtract2LinkedList.subtracting(i1Andi2[0], i1Andi2[1]);
        log.info("第二题，左边数字为0的时输出结果：" + output.formatTheLinkedListAsHumanReadableString());
    }

    public static void runTheSolutionOfSub2LinkedListTheRightNumberIsZero() {
        Subtract2LinkedList subtract2LinkedList = dependencyManager.getDependencyByType(Subtract2LinkedList.class);
        Subtract2LinkedList.LinkedList[] i1Andi2 = subtract2LinkedList.create2List(456L,0L);
        Subtract2LinkedList.Results output = subtract2LinkedList.subtracting(i1Andi2[0], i1Andi2[1]);
        log.info("第二题，右边数字为0时的输出结果：" + output.formatTheLinkedListAsHumanReadableString() + "详细测试请移步单元测试.");
    }

    public static void runTheSolutionOfSub2LinkedListLeftNumberGreaterThanRight() {
        Subtract2LinkedList subtract2LinkedList = dependencyManager.getDependencyByType(Subtract2LinkedList.class);
        Subtract2LinkedList.LinkedList[] i1Andi2 = subtract2LinkedList.create2List(456L,123L);
        Subtract2LinkedList.Results output = subtract2LinkedList.subtracting(i1Andi2[0], i1Andi2[1]);
        log.info("第二题，左边数字大于右边数字时的输出结果：" + output.formatTheLinkedListAsHumanReadableString() + "详细测试请移步单元测试.");
    }

    public static void runTheSolutionOfSub2LinkedListLeftNumberLessThanRight() {
        Subtract2LinkedList subtract2LinkedList = dependencyManager.getDependencyByType(Subtract2LinkedList.class);
        Subtract2LinkedList.LinkedList[] i1Andi2 = subtract2LinkedList.create2List(123L,456L);
        Subtract2LinkedList.Results output = subtract2LinkedList.subtracting(i1Andi2[0], i1Andi2[1]);
        log.info("第二题，左边数字小于右边数字时的输出结果：" + output.formatTheLinkedListAsHumanReadableString() + "详细测试请移步单元测试.");
    }

    public static void runTheSolutionOfSub2LinkedListOnSameNumbers() {
        Subtract2LinkedList subtract2LinkedList = dependencyManager.getDependencyByType(Subtract2LinkedList.class);
        Subtract2LinkedList.LinkedList[] i1Andi2 = subtract2LinkedList.create2List(123L,123L);
        Subtract2LinkedList.Results output = subtract2LinkedList.subtracting(i1Andi2[0], i1Andi2[1]);
        log.info("第二题，左右边数字相等时的输出结果：" + output.formatTheLinkedListAsHumanReadableString() + "详细测试请移步单元测试.");
    }

    public static void runTheSolutionOfSubtract2LinkedList() {
        runTheSolutionOfSub2LinkedListTheLeftNumberIsZero();
        runTheSolutionOfSub2LinkedListTheRightNumberIsZero();
        runTheSolutionOfSub2LinkedListLeftNumberGreaterThanRight();
        runTheSolutionOfSub2LinkedListLeftNumberLessThanRight();
        runTheSolutionOfSub2LinkedListOnSameNumbers();
    }

    public static void runTheSolutionOfStudentFilter() {
        StudentFilter studentFilter = dependencyManager.getDependencyByType(StudentFilter.class);
        List<StudentFilter.StudentScore> studentScores = new ArrayList<>(16);
        studentScores.add(new StudentFilter.StudentScore("Alice", "数学", 20));
        studentScores.add(new StudentFilter.StudentScore("Charlie", "数学", 78));
        studentScores.add(new StudentFilter.StudentScore("Eve", "数学", 99));
        studentScores.add(new StudentFilter.StudentScore("Grace", "数学", 23));
        studentScores.add(new StudentFilter.StudentScore("Ivan", "数学", 50));
        studentScores.add(new StudentFilter.StudentScore("Mallory", "数学", 38));
        studentScores.add(new StudentFilter.StudentScore("Oscar", "数学", 82));
        studentScores.add(new StudentFilter.StudentScore("Quentin", "数学", 100));

        studentScores.add(new StudentFilter.StudentScore("Bob", "语文", 50));
        studentScores.add(new StudentFilter.StudentScore("David", "语文", 55));
        studentScores.add(new StudentFilter.StudentScore("Frank", "语文", 12));
        studentScores.add(new StudentFilter.StudentScore("Heidi", "语文", 100));
        studentScores.add(new StudentFilter.StudentScore("Judy", "语文", 44));
        studentScores.add(new StudentFilter.StudentScore("Ned", "语文", 62));
        studentScores.add(new StudentFilter.StudentScore("Pat", "语文", 49));
        studentScores.add(new StudentFilter.StudentScore("Rupert", "语文", 80));

        studentFilter.addSeeds(studentScores);

        List<StudentFilter.Filter> filters = new ArrayList<>(2);
        filters.add(new StudentFilter.Filter("数学",50));
        filters.add(new StudentFilter.Filter("语文", 80));
        Set<String> names = studentFilter.filteringStudentsByFilters(filters);
        assert names.containsAll(new HashSet<>(5){{
            add("Charlie");
            add("Eve");
            add("Ivan");
            add("Oscar");
            add("Quentin");
        }});

        assert names.containsAll(new HashSet<>(){{
            add("Heidi");
            add("Rupert");
        }});
        assert names.size() == 7;

        log.info("第三题，输出结果为：" + names + "详细测试请移步单元测试.");
    }

    public static void main(String[] args) throws Exception{
        runTheSolutionOfSumOutputOf3Services();
        runTheSolutionOfSubtract2LinkedList();
        runTheSolutionOfStudentFilter();
    }
}