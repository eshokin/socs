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

public class CalculateMedianJob extends Job {

    private List<Point> mPoints;
    private PublishSubject<Double> mMedianSubject;

    @Inject
    Calculating mCalculating;

    public CalculateMedianJob(@NonNull List<Point> points, @NonNull PublishSubject<Double> medianSubject) {
        super(new Params(Priority.MID).groupBy(CalculateMedianJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
        mMedianSubject = medianSubject;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        double median = mCalculating.getMedian(mPoints);
        mMedianSubject.onNext(median);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
