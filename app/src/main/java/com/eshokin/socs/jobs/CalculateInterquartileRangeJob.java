package com.eshokin.socs.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalculateInterquartileRangeJob extends Job {

    protected CalculateInterquartileRangeJob() {
        super(new Params(Priority.MID).groupBy(CalculateInterquartileRangeJob.class.getName()));
    }

    @Override
    public void onAdded() {

        List<Integer> list = Arrays.asList(100,2,3,4,5,6,7,67,2,32);

        int min = Collections.min(list);
        int max = Collections.max(list);
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
