package home.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntStatistic {
    int countInt;
    private int min;
    private int max;
    private int sum;
    private double average;
}
