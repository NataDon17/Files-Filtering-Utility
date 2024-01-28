package home.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringStatistic {
    private int countStrings;
    private int strMinSize;
    private int strMaxSize;
}
