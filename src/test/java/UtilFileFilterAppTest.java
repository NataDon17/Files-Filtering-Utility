import home.UtilFileFilterApp;
import home.model.stats.FloatStatistic;
import home.model.stats.IntStatistic;
import home.model.stats.StringStatistic;
import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.file.Path;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static constants.OutputFileConst.OUT_FILE;
import static org.junit.Assert.*;

public class UtilFileFilterAppTest {
    private static boolean isEqual(Path firstFile, Path secondFile) {
        try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
             BufferedReader bf2 = Files.newBufferedReader(secondFile)) {
            String line;
            while ((line = bf1.readLine()) != null) {
                if (!line.equals(bf2.readLine())) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileToDirectoryWhenDirectoryPathParameterIs() {
        UtilFileFilterApp.main("-o src/test/java/TestFiles/actual in1.txt".split(" "));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/floats.txt"), Paths.get("src/test/java/TestFiles/expected/floats.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/integers.txt"), Paths.get("src/test/java/TestFiles/expected/integers.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/strings.txt"), Paths.get("src/test/java/TestFiles/expected/strings.txt")));
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileToNotExistDirectoryWhenDirectoryPathParameterIs() {
        UtilFileFilterApp.main("-o src/test/java/TestFiles/new in1.txt".split(" "));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/new/floats.txt"), Paths.get("src/test/java/TestFiles/expected/floats.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/new/integers.txt"), Paths.get("src/test/java/TestFiles/expected/integers.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/new/strings.txt"), Paths.get("src/test/java/TestFiles/expected/strings.txt")));
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileWhenInputFileNotExist() {
        String[] args = {"invalid.txt"};
        UtilFileFilterApp utilFileFilterApp = new UtilFileFilterApp();
        assertThrows("The incoming file was not found, specify an existing file", IOException.class, () -> utilFileFilterApp.run(args, OUT_FILE));
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileWhenPrefixParameterIs() {
        UtilFileFilterApp.main("-o src/test/java/TestFiles/actual -p res_ in1.txt".split(" "));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/res_floats.txt"), Paths.get("src/test/java/TestFiles/expected/res_floats.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/res_integers.txt"), Paths.get("src/test/java/TestFiles/expected/res_integers.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/res_strings.txt"), Paths.get("src/test/java/TestFiles/expected/res_strings.txt")));
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileWhenAppendParameterIs() {
        UtilFileFilterApp.main("-o src/test/java/TestFiles/actual -p a_ in1.txt".split(" "));
        UtilFileFilterApp.main("-o src/test/java/TestFiles/actual -a -p a_ in1.txt".split(" "));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/a_floats.txt"), Paths.get("src/test/java/TestFiles/expected/a_floats.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/a_integers.txt"), Paths.get("src/test/java/TestFiles/expected/a_integers.txt")));
        assertTrue(isEqual(Paths.get("src/test/java/TestFiles/actual/a_strings.txt"), Paths.get("src/test/java/TestFiles/expected/a_strings.txt")));
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileWhenShortStatsParameterIs() {
        Map<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("strings.txt", 4);
        expectedMap.put("integers.txt", 2);
        expectedMap.put("floats.txt", 2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        UtilFileFilterApp.main("-o src/test/java/TestFiles/actual -s in1.txt".split(" "));

        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream2));
        printMap(expectedMap);

        String consoleOutput = outputStream.toString();
        String expectedOutput = outputStream2.toString();
        assertEquals(consoleOutput, expectedOutput);
    }

    @Test
    @SneakyThrows
    public void shouldUploadFileWhenFullStatsParameterIs() {
        FloatStatistic floatStatistic = new FloatStatistic(2, -0.001F, 3.1415F, 3.1405F, 1.5702500343322754);
        StringStatistic stringStatistic = new StringStatistic(4, 6, 26);
        IntStatistic intStatistic = new IntStatistic(2, 45, 100500, 100545, 50272.5);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        UtilFileFilterApp.main("-o src/test/java/TestFiles/actual -f in1.txt".split(" "));

        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream2));
        System.out.println(stringStatistic);
        System.out.println(intStatistic);
        System.out.println(floatStatistic);

        String consoleOutput = outputStream.toString();
        String expectedOutput = outputStream2.toString();
        assertEquals(consoleOutput, expectedOutput);
    }

    public static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " row count: " + entry.getValue());
        }
    }
}