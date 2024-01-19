package home.service;

import home.model.stats.FloatStatistic;
import home.model.stats.IntStatistic;
import home.model.stats.StringStatistic;

import java.util.*;

public class GenerateStatistic {
    public static IntStatistic generateIntStats(List<String> lines) {
        IntStatistic intStatistic = new IntStatistic();
        intStatistic.setCountInt(lines.size());
        intStatistic.setMin(lines.stream().mapToInt(Integer::parseInt).min().orElseThrow());
        intStatistic.setMax(lines.stream().mapToInt(Integer::parseInt).max().orElseThrow());
        intStatistic.setSum(lines.stream().mapToInt(Integer::parseInt).sum());
        intStatistic.setAverage((double) intStatistic.getSum() / intStatistic.getCountInt());
        return intStatistic;
    }

    public static FloatStatistic generateFloatStats(List<String> lines) {
        FloatStatistic floatStatistic = new FloatStatistic();
        floatStatistic.setCountFloat(lines.size());
        floatStatistic.setMin((float) lines.stream().mapToDouble(Float::parseFloat).min().orElseThrow());
        floatStatistic.setMax((float) lines.stream().mapToDouble(Float::parseFloat).max().orElseThrow());
        floatStatistic.setSum((float) lines.stream().mapToDouble(Float::parseFloat).sum());
        floatStatistic.setAverage((double) floatStatistic.getSum() / floatStatistic.getCountFloat());
        return floatStatistic;
    }

    public static StringStatistic generateStringStats(List<String> lines) {
        StringStatistic stringStatistic = new StringStatistic();
        stringStatistic.setCountStrings(lines.size());
        Optional<String> strMin = lines.stream().min(Comparator.comparing(String::length));
        stringStatistic.setStrMinSize(strMin.get().length());
        Optional<String> strMax = lines.stream().max(Comparator.comparing(String::length));
        stringStatistic.setStrMaxSize(strMax.get().length());
        return stringStatistic;
    }
}
