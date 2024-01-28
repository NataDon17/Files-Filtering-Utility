package home.service;

import home.model.FileType;
import home.model.OutputFile;
import lombok.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static home.matcher.ParserFromString.isFloat;
import static home.matcher.ParserFromString.isInteger;
import static home.service.GenerateStatistic.*;

@AllArgsConstructor
@NoArgsConstructor

public class FilterFiles {
    private Map<String, List<String>> statistics = new HashMap<>();
    private Map<String, BufferedWriter> outputFiles = new HashMap<>();

    public void filterAndWriteFiles(String[] inputs, OutputFile outputFile) throws IOException {
        for (String input : inputs) {
            File file = new File(input);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (isInteger(line)) {
                    writeToFile(FileType.INTEGER.getFileType(), line, outputFile);
                } else if (isFloat(line)) {
                    writeToFile(FileType.FLOAT.getFileType(), line, outputFile);
                } else {
                    writeToFile(FileType.STRING.getFileType(), line, outputFile);
                }
            }
            br.close();
        }
        printShortStatistics(outputFile);
        printFullStatistics(outputFile);
    }

    private void writeToFile(String fileName, String data, OutputFile outputFile) throws IOException {
        File outputPathDir = new File(outputFile.getOutputPath());
        outputPathDir.mkdirs();
        BufferedWriter writer = getOutputWriter(outputFile.getOutputPath() + File.separator + outputFile.getPrefix() + fileName, outputFile);
        writer.write(data);
        writer.newLine();
        writer.flush();
        incrementStatistic(outputFile.getOutputPath() + File.separator + outputFile.getPrefix() + fileName);
    }

    private BufferedWriter getOutputWriter(String fileName, OutputFile outputFile) throws IOException {
        if (!outputFiles.containsKey(fileName)) {
            FileWriter fw = new FileWriter(fileName, outputFile.isAppendMode());
            BufferedWriter writer = new BufferedWriter(fw);
            outputFiles.put(fileName, writer);
            return writer;
        } else {
            return outputFiles.get(fileName);
        }
    }

    private void incrementStatistic(String fileType) throws IOException {
        statistics.put(fileType, Files.readAllLines(Paths.get(fileType)));
    }

    public void printShortStatistics(OutputFile outputFile) {
        if (outputFile.isShotStatistic()) {
            for (Map.Entry<String, List<String>> entry : statistics.entrySet()) {
                System.out.println(entry.getKey().substring(outputFile.getOutputPath().length() + outputFile.getPrefix().length() + 1) + " row count: " + entry.getValue().size());
            }
        }
    }

    private void printFullStatistics(OutputFile outputFile) {
        if (outputFile.isFullStatistic()) {
            for (Map.Entry<String, List<String>> entry : statistics.entrySet()) {
                if (entry.getKey().equals(outputFile.getOutputPath() + File.separator + outputFile.getPrefix() + FileType.STRING.getFileType())) {
                    System.out.println(generateStringStats(entry.getValue()));
                }
                if (entry.getKey().equals(outputFile.getOutputPath() + File.separator + outputFile.getPrefix() + FileType.INTEGER.getFileType())) {
                    System.out.println(generateIntStats(entry.getValue()));
                }
                if (entry.getKey().equals(outputFile.getOutputPath() + File.separator + outputFile.getPrefix() + FileType.FLOAT.getFileType())) {
                    System.out.println(generateFloatStats(entry.getValue()));
                }
            }
        }
    }
}
