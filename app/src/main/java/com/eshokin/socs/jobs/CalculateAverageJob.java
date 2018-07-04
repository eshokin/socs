package com.eshokin.socs.jobs;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import javax.inject.Inject;

public class CalculateAverageJob extends Job {

    private List<Point> mPoints;

    @Inject
    Calculating mCalculating;

    public CalculateAverageJob(List<Point> points) {
        super(new Params(Priority.MID).groupBy(CalculateAverageJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        double average = 0;
        if (mPoints != null && mPoints.size() > 0) {
            //average = mPoints.stream().filter(Objects::nonNull).mapToDouble(point -> point.getRate()).average().getAsDouble();
            double sum = 0d;
            for (Point point : mPoints) {
                if (point != null) {
                    sum += point.getRate();
                }
            }
            average = sum / mPoints.size();
        }
        // average
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
