package com.eshokin.socs.jobs;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import javax.inject.Inject;

public class SearchMinMaxJob extends Job {

    private List<Point> mPoints;

    @Inject
    Calculating mCalculating;

    public SearchMinMaxJob(List<Point> points) {
        super(new Params(Priority.MID).groupBy(SearchMinMaxJob.class.getName()));
        AppController.getComponent().inject(this);
        mPoints = points;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        double max = mCalculating.getMax(mPoints);
        double min = mCalculating.getMin(mPoints);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
