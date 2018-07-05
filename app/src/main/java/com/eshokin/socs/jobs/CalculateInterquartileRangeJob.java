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

public class CalculateInterquartileRangeJob extends Job {

    private List<Point> mPoints;
    private PublishSubject<Double> mInterquartileRangeSubject;
    @Inject
    Calculating mCalculating;

    public CalculateInterquartileRangeJob(@NonNull List<Point> points, @NonNull PublishSubject<Double> interquartileRangeSubject) {
        super(new Params(Priority.MID).groupBy(CalculateInterquartileRangeJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
        mInterquartileRangeSubject = interquartileRangeSubject;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        double interquartileRange = mCalculating.getInterquartileRange(mPoints);
        mInterquartileRangeSubject.onNext(interquartileRange);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
