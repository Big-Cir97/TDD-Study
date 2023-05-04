package com.springbootunittestingmocking.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ApplicationDao {

    public double addGradeResultsForSingleClass(List<Double> grades) {
        double result = 0;

        for (double i : grades) {
            result += i;
        }
        return result;
    }

    public double findGradePointAverage(List<Double> grades) {
        int lengthOfgrades = grades.size();
        double sum = addGradeResultsForSingleClass(grades);
        double result = sum / lengthOfgrades;

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        return resultRound.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Object checkNull(Object obj) {
        if (obj != null) {
            return obj;
        }
        return null;
    }
}
