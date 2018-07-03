package com.eshokin.socs.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.Date;

public class DataGenerationJob extends Job {

    private Date mStartInterval;
    private Date mEndInterval;

    public DataGenerationJob(Date startInterval, Date endInterval) {
        super(new Params(Priority.MID).groupBy(DataGenerationJob.class.getName()));
        mStartInterval = startInterval;
        mEndInterval = endInterval;
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
