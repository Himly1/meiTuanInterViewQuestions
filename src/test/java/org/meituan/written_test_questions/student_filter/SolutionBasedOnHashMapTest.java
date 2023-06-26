package org.meituan.written_test_questions.student_filter;

import org.junit.jupiter.api.Test;
import org.meituan.asnyc_promise.AsyncPromise;
import org.meituan.dependency_manager.DependencyManager;
import org.meituan.dependency_manager.HashMapBasedDependencyManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


class SolutionBasedOnHashMapTest {
    DependencyManager dependencyManager = new HashMapBasedDependencyManager();
    private Logger log = Logger.getLogger(SolutionBasedOnHashMapTest.class.getName());

    StudentFilter studentFilter = new SolutionBasedOnHashMap(dependencyManager.getDependencyByType(AsyncPromise.class));

    @Test
    void ensureAddSeedsFunctionPerformanceLessThan500Ms() {
        List<StudentFilter.StudentScore> nineHundredK = MockDataHelper.readMockDataFromTheFile(true);
        long startTime = System.currentTimeMillis();
        studentFilter.addSeeds(nineHundredK);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("加载90万条数据的结果为： "  + "耗时: " + duration +  "毫秒" +   "  加载数量: " + nineHundredK.size());
        assert duration < 500;
    }

    @Test
    void ensureFilterFunctionPerformanceLessThan200Ms() {
        List<StudentFilter.StudentScore> nineHundredK = MockDataHelper.readMockDataFromTheFile(true);
        studentFilter.addSeeds(nineHundredK);
        long startTime = System.currentTimeMillis();
        List<StudentFilter.Filter> filters = new ArrayList<>(3);
        filters.add(new StudentFilter.Filter(MockDataHelper.COURSES[0], 55));
        filters.add(new StudentFilter.Filter(MockDataHelper.COURSES[1], 80));
        filters.add(new StudentFilter.Filter(MockDataHelper.COURSES[4], 40));
        Set<String> names = studentFilter.filteringStudentsByFilters(filters);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("90万条数据，三个条件查询,结果为: "  + "耗时: " + duration +  "毫秒" +   "  符合条件的学生数量: " + names.size());
        assert duration < 200;
    }

    @Test
    void ensureTheFilterResultsIsRight() {
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
    }

    @Test
    void ensureTheFilterResultsIsRightOnDuplicatedScore() {
        List<StudentFilter.StudentScore> studentScores = new ArrayList<>(16);
        studentScores.add(new StudentFilter.StudentScore("Alice", "数学", 50));
        studentScores.add(new StudentFilter.StudentScore("Charlie", "数学", 50));
        studentScores.add(new StudentFilter.StudentScore("Eve", "数学", 25));
        studentScores.add(new StudentFilter.StudentScore("Quentin", "数学", 58));

        studentScores.add(new StudentFilter.StudentScore("Bob", "语文", 50));
        studentScores.add(new StudentFilter.StudentScore("David", "语文", 80));
        studentScores.add(new StudentFilter.StudentScore("Frank", "语文", 80));
        studentScores.add(new StudentFilter.StudentScore("Heidi", "语文", 100));

        studentFilter.addSeeds(studentScores);
        List<StudentFilter.Filter> filters = new ArrayList<>(2);
        filters.add(new StudentFilter.Filter("数学",50));
        filters.add(new StudentFilter.Filter("语文", 80));
        Set<String> names = studentFilter.filteringStudentsByFilters(filters);

        assert names.containsAll(new HashSet<>(5){{
            add("Alice");
            add("Charlie");
            add("Quentin");
        }});

        assert names.containsAll(new HashSet<>(){{
            add("David");
            add("Frank");
            add("Heidi");
        }});
        assert names.size() == 6;
    }

    @Test
    void filteringStudentsByFilters() {
    }
}