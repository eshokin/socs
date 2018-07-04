package com.eshokin.socs.jobs;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class DataGenerationJob extends Job {

    private Date mStartInterval;
    private Date mEndInterval;

    @Inject
    Calculating mCalculating;

    public DataGenerationJob(Date startInterval, Date endInterval) {
        super(new Params(Priority.MID).groupBy(DataGenerationJob.class.getName()));

        AppController.getComponent().inject(this);

        mStartInterval = startInterval;
        mEndInterval = endInterval;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        List<Point> points = mCalculating.generateStatistics(mStartInterval, mEndInterval);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
