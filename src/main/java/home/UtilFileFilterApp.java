package home;

import home.model.OutputFile;
import home.service.FilterFiles;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UtilFileFilterApp {
    public static void main(String[] args) {
        String outputPath = "src/main/java/home"; // default output path
        String prefix = "";    // Set the default prefix
        boolean appendMode = false;
        boolean shotStatistic = false;
        boolean fullStatistic = false;
        OutputFile outputFile = new OutputFile(outputPath, prefix, appendMode, shotStatistic, fullStatistic);

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(getOptions(), args);
            if (cmd.hasOption("h")) {
                HelpFormatter hf = new HelpFormatter();
                hf.printHelp("Options", getOptions());
            }
            if (cmd.hasOption("o")) {
                outputFile.setOutputPath(cmd.getOptionValue("o"));
            }
            if (cmd.hasOption("p")) {
                outputFile.setPrefix(cmd.getOptionValue("p"));
            }
            outputFile.setAppendMode(cmd.hasOption("a"));
            outputFile.setShotStatistic(cmd.hasOption("s"));
            outputFile.setFullStatistic(cmd.hasOption("f"));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid option parameter. To find out the available options, type -h");
        }

        try {
            new UtilFileFilterApp().run(getInputFiles(args), outputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("The incoming file was not found, specify an existing file");
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(String[] args, OutputFile outputFile) throws IOException {
        FilterFiles filterFiles = new FilterFiles();
        filterFiles.filterAndWriteFiles(args, outputFile);
    }

    private static String[] getInputFiles(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(getOptions(), args);
        int n;
        if (cmd.hasOption("o") && cmd.hasOption("p")) {
            n = cmd.getOptions().length + 2;
        } else if (cmd.hasOption("o") || cmd.hasOption("p")) {
            n = cmd.getOptions().length + 1;
        } else {
            n = cmd.getOptions().length;
        }
        String[] inputFiles = new String[args.length - n];
        System.arraycopy(args, n, inputFiles, 0, args.length - n);
        return inputFiles;
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "print options information");
        options.addOption("o", true, "Output directory path");
        options.addOption("p", true, "Output file name prefix");
        options.addOption("a", false, "Append to existing files");
        options.addOption("s", false, "Generate concise statistics");
        options.addOption("f", false, "Generate full statistics");
        return options;
    }
}