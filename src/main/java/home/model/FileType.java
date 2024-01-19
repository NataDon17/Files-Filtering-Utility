package home.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum FileType {
    STRING("strings.txt"),
    INTEGER("integers.txt"),
    FLOAT("floats.txt");

    final String fileType;

    FileType(String fileType) {
        this.fileType = fileType;
    }
}
