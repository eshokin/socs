package com.eshokin.socs.screens.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eshokin.socs.R;
import com.eshokin.socs.api.enumerations.Interval;
import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.screens.main.adapter.IntervalAdapter;
import com.eshokin.socs.screens.main.di.DaggerMainComponent;
import com.eshokin.socs.screens.main.di.MainComponent;
import com.eshokin.socs.screens.main.di.MainModule;
import com.eshokin.socs.utils.IntervalHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final String FORMAT_DD_MM_YYYY_HH_MM = "dd.MM.yyyy, HH:mm";

    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.activity_main_min_value)
    TextView mMin;

    @BindView(R.id.activity_main_min_waiting)
    ProgressBar mMinWaiting;

    @BindView(R.id.activity_main_max_value)
    TextView mMax;

    @BindView(R.id.activity_main_max_waiting)
    ProgressBar mMaxWaiting;

    @BindView(R.id.activity_main_average_value)
    TextView mAverage;

    @BindView(R.id.activity_main_average_waiting)
    ProgressBar mAverageWaiting;

    @BindView(R.id.activity_main_median_value)
    TextView mMedian;

    @BindView(R.id.activity_main_median_waiting)
    ProgressBar mMedianWaiting;

    @BindView(R.id.activity_main_interquartile_range_value)
    TextView mInterquartileRange;

    @BindView(R.id.activity_main_interquartile_range_waiting)
    ProgressBar mInterquartileRangeWaiting;

    @BindView(R.id.activity_main_start_date)
    EditText mStartDate;

    @BindView(R.id.activity_main_end_date)
    EditText mEndDate;

    @BindView(R.id.activity_main_chose_interval)
    Spinner mChoseInterval;

    @BindView(R.id.activity_main_load_button)
    Button mLoadButton;

    @BindView(R.id.activity_main_chart)
    LineChart mChart;

    private AlertDialog mAlertDialog;
    private DatePickerDialog mStartDatePickerDialog;
    private DatePickerDialog mEndDatePickerDialog;

    private TimePickerDialog mStartTimePicker;
    private TimePickerDialog mEndTimePicker;

    private SimpleDateFormat mDateFormat;

    private IntervalAdapter mIntervalAdapter;

    private MainComponent mMainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainComponent = DaggerMainComponent.builder().mainModule(new MainModule(this)).appComponent(AppController.getComponent()).build();
        mMainPresenter.ingest(mMainComponent);

        initInterval();

        mDateFormat = new SimpleDateFormat(FORMAT_DD_MM_YYYY_HH_MM, getLocale());

        mStartDate.setOnClickListener(view -> {
            mMainPresenter.showStartDateDialog();
        });

        mEndDate.setOnClickListener(view -> {
            mMainPresenter.showEndDialog();
        });

        mLoadButton.setOnClickListener(view -> {
            Interval interval = Interval.H4;
            Pair<Interval, Integer> intervalItem = (Pair<Interval, Integer>) mChoseInterval.getSelectedItem();
            if (intervalItem != null) {
                interval = intervalItem.first;
            }
            mMainPresenter.loadStatistics(interval);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMainComponent = null;
    }

    @Override
    public void showLoading(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mStartDate.setEnabled(!show);
        mEndDate.setEnabled(!show);
        mChoseInterval.setEnabled(!show);
        mLoadButton.setEnabled(!show);
    }

    @Override
    public void showAlertDialog(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.activity_main_attention)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(R.string.activity_main_button_ok,
                        (dialog, id) -> {
                            mMainPresenter.hideAlertDialog();
                            dialog.cancel();
                        });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    @Override
    public void hideAlertDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    @Override
    public void showPoints(List<Point> points) {

        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
        setData(points);
        mChart.invalidate();
        mChart.fitScreen();
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(170f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }


    @Override
    public void showMinMaxValue(Double min, Double max) {
        mMin.setText(String.format(getLocale(), "%.2f", min));
        mMax.setText(String.format(getLocale(), "%.2f", max));
    }

    @Override
    public void showCalculatingMinMaxValue(boolean show) {
        mMinWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
        mMaxWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            mMin.setText("");
            mMax.setText("");
        }
    }

    @Override
    public void showAverageValue(Double average) {
        mAverage.setText(String.format(getLocale(), "%.2f", average));
    }

    @Override
    public void showCalculatingAverageValue(boolean show) {
        mAverageWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            mAverage.setText("");
        }
    }

    @Override
    public void showMedianValue(Double median) {
        mMedian.setText(String.format(getLocale(), "%.2f", median));
    }

    @Override
    public void showCalculatingMedianValue(boolean show) {
        mMedianWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            mMedian.setText("");
        }
    }

    @Override
    public void showInterquartileRangeValue(Double interquartileRange) {
        mInterquartileRange.setText(String.format(getLocale(), "%.2f", interquartileRange));
    }

    @Override
    public void showCalculatingInterquartileRangeValue(boolean show) {
        mInterquartileRangeWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            mInterquartileRange.setText("");
        }
    }

    @Override
    public void hideDateDialog() {
        if (mStartDatePickerDialog != null && mStartDatePickerDialog.isShowing()) {
            mStartDatePickerDialog.dismiss();
        }

        if (mEndDatePickerDialog != null && mEndDatePickerDialog.isShowing()) {
            mEndDatePickerDialog.dismiss();
        }
    }

    @Override
    public void hideTimeDialog() {
        if (mStartTimePicker != null && mStartTimePicker.isShowing()) {
            mStartTimePicker.dismiss();
        }

        if (mEndTimePicker != null && mEndTimePicker.isShowing()) {
            mEndTimePicker.dismiss();
        }
    }

    @Override
    public void setStartDate(Date startDate) {
        mStartDate.setError(null);
        mStartDate.setText(dateToString(startDate));
    }

    @Override
    public void setEndDate(Date endDate) {
        mEndDate.setError(null);
        mEndDate.setText(dateToString(endDate));
    }

    @Override
    public void emptyStartDate() {
        mStartDate.setError(getString(R.string.activity_main_required_field));
        mStartDate.requestFocus();
    }

    @Override
    public void emptyEndDate() {
        mEndDate.setError(getString(R.string.activity_main_required_field));
        mEndDate.requestFocus();
    }

    @Override
    public void showStartDateDialog(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        mStartDatePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            calendar.set(year1, monthOfYear, dayOfMonth);
            mMainPresenter.hideDateDialog();
            mMainPresenter.showStartTimeDialog(calendar.getTime());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mStartDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        mStartDatePickerDialog.setOnCancelListener(dialog -> {
            mMainPresenter.hideDateDialog();
        });
        mStartDatePickerDialog.show();
    }

    @Override
    public void showStartTimeDialog(Date startTime) {
        final Calendar calender = Calendar.getInstance();
        calender.setTime(startTime);
        mStartTimePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            mMainPresenter.setStartDate(calender.getTime());
            mMainPresenter.hideTimeDialog();
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true);
        mStartTimePicker.setTitle(R.string.activity_main_select_time);
        mStartTimePicker.show();
    }

    @Override
    public void showEndDateDialog(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);

        mEndDatePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            calendar.set(year1, monthOfYear, dayOfMonth);
            mMainPresenter.hideDateDialog();
            mMainPresenter.showEndTimeDialog(calendar.getTime());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mEndDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        mEndDatePickerDialog.setOnCancelListener(dialog -> {
            mMainPresenter.hideDateDialog();
        });
        mEndDatePickerDialog.show();
    }

    @Override
    public void showEndTimeDialog(Date endTime) {
        final Calendar calender = Calendar.getInstance();
        calender.setTime(endTime);
        mEndTimePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            mMainPresenter.setEndDate(calender.getTime());
            mMainPresenter.hideTimeDialog();
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true);
        mEndTimePicker.setTitle(R.string.activity_main_select_time);
        mEndTimePicker.show();
    }

    private void initInterval() {
        ArrayList<Pair<Interval, Integer>> itemList = new ArrayList<>();
        TreeMap<Interval, Integer> intervalList = IntervalHelper.getIntervalResources();
        for (Map.Entry<Interval, Integer> interval : intervalList.entrySet()) {
            itemList.add(new Pair<>(interval.getKey(), interval.getValue()));
        }

        mIntervalAdapter = new IntervalAdapter(this, R.layout.spinner_item, itemList);
        mChoseInterval.setAdapter(mIntervalAdapter);
    }

    private Locale getLocale() {
        Resources resources = this.getResources();
        if (resources != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return resources.getConfiguration().getLocales().get(0);
            } else {
                return resources.getConfiguration().locale;
            }
        }
        return Locale.US;
    }

    private String dateToString(Date date) {
        if (date != null) {
            try {
                return mDateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }



    private void setData(List<Point> points) {

        ArrayList<Entry> values = new ArrayList<Entry>();



        for (Point point : points) {
            if (point != null && point.getDate() != null && point.getRate() != null) {
                values.add(new Entry(point.getDate().getTime(), point.getRate().floatValue()));
            }
        }

        // create a dataset and give it a type
        LineDataSet set = new LineDataSet(values, "DataSet 1");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setValueTextColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(1.5f);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawCircleHole(false);

        // create a data object with the datasets
        LineData data = new LineData(set);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        mChart.setData(data);
    }
}
