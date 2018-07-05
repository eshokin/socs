package com.eshokin.socs.screens.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void showLoading(boolean show);

    void showDialog(int message);

    void hideDialog();

    void showMinMaxValue(Double min, Double max);

    void showCalculatingMinMaxValue(boolean show);

    void showAverageValue(Double average);

    void showCalculatingAverageValue(boolean show);

    void showMedianValue(Double median);

    void showCalculatingMedianValue(boolean show);

    void showInterquartileRangeValue(Double interquartileRange);

    void showCalculatingInterquartileRangeValue(boolean show);
}
