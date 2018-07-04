package com.eshokin.socs.calculating;

import com.eshokin.socs.api.schemas.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Calculating {

    private double mRange = 120d;

    private Comparator mComparator = (Comparator<Point>) (point1, point12) ->
            Double.compare(point1 != null ? point1.getRate() : 0, point12 != null ? point12.getRate() : 0);

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

    public double getMin(List<Point> points) {
        //double min = mPoints.stream().filter(Objects::nonNull).mapToDouble(point -> point.getRate()).min().getAsDouble();
        if (points != null && points.size() > 0) {
            Point min = Collections.min(points, mComparator);
            return min != null ? min.getRate() : 0;
        }
        return 0;
    }

    public double getMax(List<Point> points) {
        //double max = mPoints.stream().filter(Objects::nonNull).mapToDouble(point -> point.getRate()).max().getAsDouble();
        if (points != null && points.size() > 0) {
            Point max = Collections.max(points, mComparator);
            return max != null ? max.getRate() : 0;
        }
        return 0;
    }

    private Double getRate() {
        Random rand = new Random();
        mRange += (rand.nextInt(21) - 10) / 10.0;
        return mRange;
    }
}
