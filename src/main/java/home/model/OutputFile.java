package home.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OutputFile {
    private String outputPath;
    private String prefix;
    private boolean appendMode;
    private boolean shotStatistic;
    private boolean fullStatistic;

}
