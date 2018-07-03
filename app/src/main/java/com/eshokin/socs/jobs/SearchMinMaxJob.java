package com.eshokin.socs.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchMinMaxJob extends Job {

    protected SearchMinMaxJob() {
        super(new Params(Priority.MID).groupBy(SearchMinMaxJob.class.getName()));
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        List<Integer> list = Arrays.asList(100,2,3,4,5,6,7,67,2,32);

        int min = Collections.min(list);
        int max = Collections.max(list);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
