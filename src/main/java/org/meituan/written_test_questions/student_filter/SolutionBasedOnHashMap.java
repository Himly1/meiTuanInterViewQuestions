package org.meituan.written_test_questions.student_filter;

import org.meituan.asnyc_promise.AsyncPromise;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 假定以下情况：
 * 学生分数最高100分，最低0分
 * 假定addSeeds方法传递的参数数据大小不超过100M, 大概90万条。
 * 假定单次调用addSeeds方法传递的学生成绩不少于10万条。
*/
public class SolutionBasedOnHashMap implements StudentFilter {
    private final Logger log = Logger.getLogger(SolutionBasedOnHashMap.class.getName());

    private final AsyncPromise asyncPromise;

    public SolutionBasedOnHashMap(AsyncPromise asyncPromise) {
        this.asyncPromise = asyncPromise;
    }


    private final ConcurrentHashMap<String, List<StudentScore>> courseAndStudentScoreInAscOrderMapping = new ConcurrentHashMap<>(16);
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> scoreIndex = new ConcurrentHashMap<>(16);

    private Map<String, List<StudentScore>> groupByCourse(List<StudentScore> studentScores) {
        return studentScores.stream().parallel().collect(Collectors.groupingBy(StudentScore::course));
    }

    private void updateIndex(String course, List<StudentScore> scores) {
        ConcurrentHashMap<Integer, Integer> index = scoreIndex.getOrDefault(course, new ConcurrentHashMap<>());
        //update all index.
        for (int i = 0; i < scores.size(); ++i) {
            StudentScore score = scores.get(i);
            index.putIfAbsent(score.score(), i);
        }

        scoreIndex.put(course, index);
    }

    synchronized private void putItToMapping(Map<String, List<StudentScore>> categories) {
        categories.keySet().forEach((course) -> {
            List<StudentScore> studentScores = courseAndStudentScoreInAscOrderMapping.getOrDefault(course, new ArrayList<>(16));
            studentScores.addAll(categories.get(course));

            //确保list中的数据是按成绩从低到高有序的
            studentScores.sort(Comparator.comparing(StudentScore::score));
            courseAndStudentScoreInAscOrderMapping.put(course, studentScores);
            updateIndex(course, studentScores);
        });
    }

    private void addSeedsByChunks(List<StudentScore> studentScores) {
        //这里最好是基于CPU的核心数设置chunks的数量，因此假定4个核心数。
        List<List<StudentScore>> chunks = IntStream.range(0, 4)
                .mapToObj(i -> studentScores.subList(i * studentScores.size() / 4, (i + 1) * studentScores.size() / 4))
                .toList();

        List<AsyncPromise.Promise> promises = new ArrayList<>(4);
        chunks.forEach(chunk -> {
            AsyncPromise.Promise<Object> promise = asyncPromise.promise(() -> {
                Map<String, List<StudentScore>> categories = groupByCourse(chunk);
                putItToMapping(categories);
                return null;
            });

            promises.add(promise);
        });
        for (AsyncPromise.Promise promise : promises) {
            try {
                promise.await();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addSeeds(List<StudentScore> studentScores) {
        if (studentScores.size() >= 1000 * 20) {
            addSeedsByChunks(studentScores);
            return;
        }

        Map<String, List<StudentScore>> rs = groupByCourse(studentScores);
        putItToMapping(rs);
    }

    @Override
    public Set<String> filteringStudentsByFilters(List<Filter> filters) {
        Set<String> studentNames = new HashSet<>(16);

        for (Filter filter : filters) {
            List<StudentScore> studentScores = courseAndStudentScoreInAscOrderMapping.get(filter.course());
            Map<Integer, Integer> scoreIndexOrDefault = scoreIndex.getOrDefault(filter.course(), new ConcurrentHashMap<>(0));
            Optional<Integer> index = scoreIndexOrDefault.keySet().stream().filter((scoreIndex) -> scoreIndex >= filter.score()).findFirst();

            index.ifPresent((theKeyToLoopUpIndex) -> {
                Integer realIndex = scoreIndexOrDefault.get(index.get());
                List<StudentScore> matched = studentScores.subList(realIndex, studentScores.size());
                matched.forEach(studentScore -> studentNames.add(studentScore.name()));
            });
        }

        return studentNames;
    }
}