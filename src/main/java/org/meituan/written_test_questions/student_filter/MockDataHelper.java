package org.meituan.written_test_questions.student_filter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MockDataHelper {

    private static final String FILENAME = "studentScores.txt";
    private static final int BATCH_SIZE = 100000;
    public static final String[] COURSES = {"论语", "诗经", "尚书", "周易", "礼记", "春秋", "左传", "史记", "汉书", "资治通鉴"};
    private static final String[] NAMES = {"张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十", "郑十一", "王十二"};

    public static void generateUniqueRecordsAndSaveItToFile(Integer maxRecords, boolean onTest){
        try {
            Path filePath = Paths.get("src", onTest ? "test" : "main", "resources", "mockData", FILENAME);
            Path parentDir = filePath.getParent();
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            Files.writeString(filePath, "");

            Random random = new Random();
            Set<String> usedNames = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < maxRecords;) {

                int randomWithThreadLocalRandomInARange = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

                String name = NAMES[random.nextInt(NAMES.length)] + randomWithThreadLocalRandomInARange;
                if (!usedNames.contains(name)) {
                    usedNames.add(name);
                    String course = COURSES[random.nextInt(COURSES.length)];
                    int score = random.nextInt(101);
                    sb.append(name).append(",").append(course).append(",").append(score).append("\n");
                    i++;
                }

                if (i > BATCH_SIZE) {
                    Files.writeString(filePath, sb.toString(), StandardOpenOption.APPEND);
                    sb.setLength(0);
                }
            }
            if (sb.length() > 0) {
                Files.writeString(filePath, sb.toString(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<StudentFilter.StudentScore> readMockDataFromTheFile(boolean onTest) {
        List<StudentFilter.StudentScore> studentScores = new ArrayList<>();
        try {
            Path filePath = Paths.get("src",  onTest ? "test" : "main", "resources", "mockData", FILENAME);
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + FILENAME);
            }
            BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    throw new RuntimeException("Invalid record format: " + line);
                }
                String name = parts[0];
                String course = parts[1];
                int score = Integer.parseInt(parts[2]);
                studentScores.add(new StudentFilter.StudentScore(name, course, score));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentScores;
    }
}
