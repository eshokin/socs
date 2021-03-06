package com.eshokin.socs.screens.main;

import com.arellomobile.mvp.InjectViewState;
import com.eshokin.socs.R;
import com.eshokin.socs.api.ApiService;
import com.eshokin.socs.api.enumerations.Interval;
import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.api.schemas.responses.GetStatisticsMethodResponse;
import com.eshokin.socs.calculating.Calculating.MinMax;
import com.eshokin.socs.jobs.CalculateAverageJob;
import com.eshokin.socs.jobs.CalculateInterquartileRangeJob;
import com.eshokin.socs.jobs.CalculateMedianJob;
import com.eshokin.socs.jobs.CalculateMinMaxJob;
import com.eshokin.socs.screens.base.BasePresenter;
import com.eshokin.socs.screens.main.di.MainComponent;
import com.eshokin.socs.utils.Rx;
import com.path.android.jobqueue.JobManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private Date mStartDate;
    private Date mEndDate;
    private PublishSubject<MinMax> mMinMaxSubject = PublishSubject.create();
    private PublishSubject<Double> mAverageSubject = PublishSubject.create();
    private PublishSubject<Double> mMedianSubject = PublishSubject.create();
    private PublishSubject<Double> mInterquartileRangeSubject = PublishSubject.create();

    @Inject
    ApiService mApiService;

    @Inject
    JobManager mJobManager;

    @Inject
    Rx mRx;

    public void ingest(MainComponent component) {
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initSubjects();
    }

    public void hideAlertDialog() {
        getViewState().hideAlertDialog();
    }

    public void hideDateDialog() {
        getViewState().hideDateDialog();
    }

    public void hideTimeDialog() {
        getViewState().hideTimeDialog();
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            if (mEndDate != null) {
                if (startDate.getTime() < mEndDate.getTime()) {
                    mStartDate = startDate;
                } else {
                    getViewState().showAlertDialog(R.string.activity_main_start_date_error);
                }
            } else {
                mStartDate = startDate;
            }
            getViewState().setStartDate(mStartDate);
        }
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            if (mStartDate != null) {
                if (endDate.getTime() > mStartDate.getTime()) {
                    mEndDate = endDate;
                } else {
                    getViewState().showAlertDialog(R.string.activity_main_end_date_error);
                }
            } else {
                mEndDate = endDate;
            }
            getViewState().setEndDate(mEndDate);
        }
    }

    public void showStartDateDialog() {
        Calendar calendar = Calendar.getInstance();
        if (mStartDate != null) {
            calendar.setTime(mStartDate);
        }
        getViewState().showStartDateDialog(calendar.getTime());
    }

    public void showStartTimeDialog(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        getViewState().showStartTimeDialog(calendar.getTime());
    }

    public void showEndTimeDialog(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        getViewState().showEndTimeDialog(calendar.getTime());
    }

    public void showEndDialog() {
        Calendar calendar = Calendar.getInstance();
        if (mEndDate != null) {
            calendar.setTime(mEndDate);
        }
        getViewState().showEndDateDialog(calendar.getTime());
    }

    public void loadStatistics(Interval interval) {

        if (mStartDate == null) {
            getViewState().emptyStartDate();
            return;
        }

        if (mEndDate == null) {
            getViewState().emptyEndDate();
            return;
        }

        getViewState().showLoading(true);
        GetStatisticsMethodRequest request = new GetStatisticsMethodRequest();
        request.setStartDate(mStartDate);
        request.setEndDate(mEndDate);
        request.setInterval(interval);

        final Observable<GetStatisticsMethodResponse> observable = mApiService.getStatisticsMethod(request);
        Disposable disposable = observable
                .compose(mRx.applySchedulers())
                .subscribe(response -> {
                    getViewState().showLoading(false);
                    if (response != null && response.getStatus() != null && response.getStatus().isOk() && response.getResult().getPoints() != null) {
                        List<Point> points = response.getResult().getPoints();
                        if (points.size() > 0) {
                            getViewState().showPoints(points);
                            calculateMinMax(points);
                            calculateAverage(points);
                            calculateMedian(points);
                            calculateInterquartileRange(points);
                        } else {
                            getViewState().showAlertDialog(R.string.could_not_retrieve_data);
                        }
                    } else {
                        getViewState().showAlertDialog(R.string.server_internal_error);
                    }
                }, error -> {
                    getViewState().showLoading(false);
                    getViewState().showAlertDialog(R.string.server_not_responding);
                });
        unSubscribeOnDestroy(disposable);
    }

    private void calculateMinMax(List<Point> points) {
        getViewState().showCalculatingMinMaxValue(true);
        mJobManager.addJob(new CalculateMinMaxJob(points, mMinMaxSubject));
    }

    private void calculateAverage(List<Point> points) {
        getViewState().showCalculatingAverageValue(true);
        mJobManager.addJob(new CalculateAverageJob(points, mAverageSubject));
    }

    private void calculateMedian(List<Point> points) {
        getViewState().showCalculatingMedianValue(true);
        mJobManager.addJob(new CalculateMedianJob(points, mMedianSubject));
    }

    private void calculateInterquartileRange(List<Point> points) {
        getViewState().showCalculatingInterquartileRangeValue(true);
        mJobManager.addJob(new CalculateInterquartileRangeJob(points, mInterquartileRangeSubject));
    }

    private void initSubjects() {
        Disposable minMaxDisposable = mMinMaxSubject
                .compose(mRx.applySchedulers())
                .subscribe(minMax -> {
                    getViewState().showCalculatingMinMaxValue(false);
                    getViewState().showMinMaxValue(minMax.getMin(), minMax.getMax());
                });
        unSubscribeOnDestroy(minMaxDisposable);

        Disposable averageDisposable = mAverageSubject.compose(mRx.applySchedulers())
                .subscribe(average -> {
                    getViewState().showCalculatingAverageValue(false);
                    getViewState().showAverageValue(average);
                });
        unSubscribeOnDestroy(averageDisposable);

        Disposable medianDisposable = mMedianSubject.compose(mRx.applySchedulers())
                .subscribe(median -> {
                    getViewState().showCalculatingMedianValue(false);
                    getViewState().showMedianValue(median);
                });
        unSubscribeOnDestroy(medianDisposable);

        Disposable interquartileRangeDisposable = mInterquartileRangeSubject.compose(mRx.applySchedulers())
                .subscribe(interquartileRange -> {
                    getViewState().showCalculatingInterquartileRangeValue(false);
                    getViewState().showInterquartileRangeValue(interquartileRange);
                });
        unSubscribeOnDestroy(interquartileRangeDisposable);
    }
}
