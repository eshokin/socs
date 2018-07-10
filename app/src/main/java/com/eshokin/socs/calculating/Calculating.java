package com.eshokin.socs.calculating;

import com.eshokin.socs.api.enumerations.Interval;
import com.eshokin.socs.api.schemas.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Calculating {

    private double mRange;

    private Comparator mComparator = (Comparator<Point>) (point1, point12) ->
            Double.compare(point1 != null ? point1.getRate() : 0, point12 != null ? point12.getRate() : 0);

    public MinMax getMinMax(List<Point> points) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        if (points != null && points.size() > 0) {
            for (Point point : points) {
                if (point != null) {
                    double rate = point.getRate();
                    if (rate < min) {
                        min = rate;
                    }
                    if (rate > max) {
                        max = rate;
                    }
                }
            }
        }
        return new MinMax(min, max);
    }

    public double getAverage(List<Point> points) {
        if (points != null && points.size() > 0) {
            //double average = points.stream().filter(Objects::nonNull).mapToDouble(point -> point.getRate()).average().getAsDouble();
            double sum = 0d;
            for (Point point : points) {
                if (point != null) {
                    sum += point.getRate();
                }
            }
            return sum / points.size();
        }
        return 0;
    }

    public synchronized double getMedian(List<Point> points) {
        if (points != null && points.size() > 0) {
            if (points.size() == 1) {
                return points.get(0).getRate();
            }

            Collections.sort(points, mComparator);
            if (points.size() % 2 == 1) {
                Point point = points.get((points.size() / 2) - 1);
                return point == null ? 0 : point.getRate();
            } else {
                Point point1 = points.get((points.size() / 2) - 1);
                Point point2 = points.get((points.size() / 2));
                return point1 != null && point2 != null ? (point1.getRate() + point2.getRate()) / 2 : 0;
            }
        }
        return 0;
    }

    public double getInterquartileRange(List<Point> points) {
        if (points != null && points.size() > 0) {
            if (points.size() == 1) {
                return points.get(0).getRate();
            }
            Collections.sort(points, mComparator);
            int middle = (points.size() / 2);
            List<Point> leftHalf = points.subList(0, middle);
            List<Point> rightHalf = points.subList(middle + points.size() % 2, points.size());
            double leftHalfMedian = getMedian(leftHalf);
            double rightHalfMedian = getMedian(rightHalf);
            return rightHalfMedian - leftHalfMedian;
        }
        return 0;
    }

    public List<Point> generateStatistics(Date startDate, Date endDate, Interval interval) {
        mRange = 100d;
        int time_interval = interval != null ? interval.getIdentifier() : Interval.H4.getIdentifier();
        List<Point> points = new ArrayList<>();
        if (startDate != null && endDate != null) {
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            if (endTime > startTime) {
                while (endTime >= startTime) {
                    startTime += time_interval;
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

    public final class MinMax {

        private final double min;
        private final double max;

        public MinMax(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }
}
