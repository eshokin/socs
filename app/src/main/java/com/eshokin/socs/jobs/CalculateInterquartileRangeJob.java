package com.eshokin.socs.jobs;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CalculateInterquartileRangeJob extends Job {

    private List<Point> mPoints;

    @Inject
    Calculating mCalculating;

    public CalculateInterquartileRangeJob(List<Point> points) {
        super(new Params(Priority.MID).groupBy(CalculateInterquartileRangeJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
