package com.eshokin.socs.jobs;

import android.support.annotation.NonNull;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;

public class CalculateAverageJob extends Job {

    private List<Point> mPoints;
    private PublishSubject<Double> mAverageSubject;

    @Inject
    Calculating mCalculating;

    public CalculateAverageJob(@NonNull List<Point> points, @NonNull PublishSubject<Double> averageSubject) {
        super(new Params(Priority.MID).groupBy(CalculateAverageJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
        mAverageSubject = averageSubject;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        double average = mCalculating.getAverage(mPoints);
        mAverageSubject.onNext(average);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
