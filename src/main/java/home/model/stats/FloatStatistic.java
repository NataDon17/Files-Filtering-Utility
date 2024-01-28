package home.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloatStatistic {
    private int countFloat;
    private float min;
    private float max;
    private float sum;
    private double average;
}
