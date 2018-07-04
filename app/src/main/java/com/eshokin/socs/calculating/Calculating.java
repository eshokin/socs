package com.eshokin.socs.calculating;

import com.eshokin.socs.api.schemas.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Calculating {

    private double mRange = 120d;

    public List<Point> generateStatistics(Date startInterval, Date endInterval) {
        List<Point> points = new ArrayList<>();
        if (startInterval != null && endInterval != null) {
            long startTime = startInterval.getTime();
            long endTime = endInterval.getTime();
            if (endTime > startTime) {
                while (endTime >= startTime) {
                    startTime += 10 * 60 * 1000;
                    Point point = new Point();
                    point.setDate(new Date(startTime));
                    point.setRate(getRate());
                    points.add(point);
                }
            }
        }
        return points;
    }

    private Double getRate() {
        Random rand = new Random();
        mRange += (rand.nextInt(21) - 10) / 10.0;
        return mRange;
    }
}
