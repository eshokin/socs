package com.eshokin.socs.screens.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.eshokin.socs.api.schemas.Point;

import java.util.Date;
import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void showLoading(boolean show);

    void showAlertDialog(int message);

    void hideAlertDialog();

    void showPoints(List<Point> points);

    void showMinMaxValue(Double min, Double max);

    void showCalculatingMinMaxValue(boolean show);

    void showAverageValue(Double average);

    void showCalculatingAverageValue(boolean show);

    void showMedianValue(Double median);

    void showCalculatingMedianValue(boolean show);

    void showInterquartileRangeValue(Double interquartileRange);

    void showCalculatingInterquartileRangeValue(boolean show);

    void showStartIntervalDateDialog(Date startInterval);

    void showStartTimeIntervalDialog(Date startInterval);

    void showEndIntervalDateDialog(Date endInterval);

    void showEndTimeIntervalDialog(Date endInterval);

    void hideDateDialog();

    void hideTimeDialog();

    void setStartInterval(Date startInterval);

    void setEndInterval(Date endInterval);

    void emptyStartInterval();

    void emptyEndInterval();
}
