package com.eshokin.socs.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

public class CalculateAverageJob extends Job {

    protected CalculateAverageJob() {
        super(new Params(Priority.MID).groupBy(CalculateAverageJob.class.getName()));
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
