package org.meituan.written_test_questions.student_filter;

import java.util.List;
import java.util.Set;

/**
 * 第三题
 * 给你一个列表List<StudentScore>，记录了学生每门课程的分数。
 * 例如：
 * 张小明 语文 80
 * 张小明 数学 95
 * 王雪花 语文90
 * 王雪花 数学 100
 * public class StudentScore {
 *     private String name;
 *     private String course;
 *     private Integer score;
 * }
 *
 * 给你一个列表List<FilterScore>，记录了分数筛选条件。
 * 例如：
 * 语文 80
 * 数学 90
 * public class FilterScore {
 *     private String course;
 *     private Integer score;
 * }
 *
 *
 * 请实现一个函数，过滤出课程分数大于等于筛选条件的学生的名字。
 */
public interface StudentFilter {
    record StudentScore(String name, String course, Integer score) {
    }

    record Filter(String course, Integer score) {
    }

    void addSeeds(List<StudentScore> studentScores);

    Set<String> filteringStudentsByFilters(List<Filter> filters);
}
