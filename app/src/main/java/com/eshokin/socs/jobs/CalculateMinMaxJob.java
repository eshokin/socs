package com.eshokin.socs.jobs;

import android.support.annotation.NonNull;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.eshokin.socs.calculating.Calculating.MinMax;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;

public class CalculateMinMaxJob extends Job {

    private List<Point> mPoints;
    private PublishSubject<MinMax> mMinMaxSubject;

    @Inject
    Calculating mCalculating;

    public CalculateMinMaxJob(@NonNull List<Point> points, @NonNull PublishSubject<MinMax> minMaxSubject) {
        super(new Params(Priority.MID).groupBy(CalculateMinMaxJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
        mMinMaxSubject = minMaxSubject;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        MinMax minMax = mCalculating.getMinMax(mPoints);
        mMinMaxSubject.onNext(minMax);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
