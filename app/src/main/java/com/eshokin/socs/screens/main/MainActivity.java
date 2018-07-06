package com.eshokin.socs.screens.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eshokin.socs.R;
import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.application.AppController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @BindView(R.id.activity_main_start_interval)
    EditText mStartInterval;

    @BindView(R.id.activity_main_end_interval)
    EditText mEndInterval;

    @BindView(R.id.activity_main_load_button)
    Button mLoadButton;

    private AlertDialog mAlertDialog;
    private DatePickerDialog mStartPickerDialog;
    private DatePickerDialog mEndPickerDialog;

    private TimePickerDialog mStartTimePicker;
    private TimePickerDialog mEndTimePicker;

    private SimpleDateFormat mDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        AppController.getComponent().inject(this);

        mDateFormat = new SimpleDateFormat(FORMAT_DD_MM_YYYY_HH_MM, getLocale());

        mStartInterval.setOnClickListener(view -> {
            mMainPresenter.showStartDateIntervalDialog();
        });

        mEndInterval.setOnClickListener(view -> {
            mMainPresenter.showEndIntervalDialog();
        });

        mLoadButton.setOnClickListener(view -> {
            mMainPresenter.loadStatistics();
        });
    }

    @Override
    public void showLoading(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mStartInterval.setEnabled(!show);
        mEndInterval.setEnabled(!show);
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
        // paint plot
    }

    @Override
    public void showMinMaxValue(Double min, Double max) {
        mMin.setText(String.valueOf(min));
        mMax.setText(String.valueOf(max));
    }

    @Override
    public void showCalculatingMinMaxValue(boolean show) {
        mMinWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
        mMaxWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAverageValue(Double average) {
        mAverage.setText(String.valueOf(average));
    }

    @Override
    public void showCalculatingAverageValue(boolean show) {
        mAverageWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMedianValue(Double median) {
        mMedian.setText(String.valueOf(median));
    }

    @Override
    public void showCalculatingMedianValue(boolean show) {
        mMedianWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showInterquartileRangeValue(Double interquartileRange) {
        mInterquartileRange.setText(String.valueOf(interquartileRange));
    }

    @Override
    public void showCalculatingInterquartileRangeValue(boolean show) {
        mInterquartileRangeWaiting.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void hideDateDialog() {
        if (mStartPickerDialog != null && mStartPickerDialog.isShowing()) {
            mStartPickerDialog.dismiss();
        }

        if (mEndPickerDialog != null && mEndPickerDialog.isShowing()) {
            mEndPickerDialog.dismiss();
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
    public void setStartInterval(Date startInterval) {
        mStartInterval.setText(dateToString(startInterval));
    }

    @Override
    public void setEndInterval(Date endInterval) {
        mEndInterval.setText(dateToString(endInterval));
    }

    @Override
    public void emptyStartInterval() {
        mStartInterval.setError(getString(R.string.activity_main_required_field));
        mStartInterval.requestFocus();
    }

    @Override
    public void emptyEndInterval() {
        mEndInterval.setError(getString(R.string.activity_main_required_field));
        mEndInterval.requestFocus();
    }

    @Override
    public void showStartIntervalDateDialog(Date startInterval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startInterval);

        mStartPickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            calendar.set(year1, monthOfYear, dayOfMonth);
            mMainPresenter.hideDateDialog();
            mMainPresenter.showStartTimeIntervalDialog(calendar.getTime());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mStartPickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        mStartPickerDialog.setOnCancelListener(dialog -> {
            mMainPresenter.hideDateDialog();
        });
        mStartPickerDialog.show();
    }

    @Override
    public void showStartTimeIntervalDialog(Date startInterval) {
        final Calendar calender = Calendar.getInstance();
        calender.setTime(startInterval);
        mStartTimePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            mMainPresenter.setStartIntervalDate(calender.getTime());
            mMainPresenter.hideTimeDialog();
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true);
        mStartTimePicker.setTitle(R.string.activity_main_select_time);
        mStartTimePicker.show();
    }

    @Override
    public void showEndIntervalDateDialog(Date endInterval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endInterval);

        mEndPickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            calendar.set(year1, monthOfYear, dayOfMonth);
            mMainPresenter.hideDateDialog();
            mMainPresenter.showEndTimeIntervalDialog(calendar.getTime());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mEndPickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        mEndPickerDialog.setOnCancelListener(dialog -> {
            mMainPresenter.hideDateDialog();
        });
        mEndPickerDialog.show();
    }

    @Override
    public void showEndTimeIntervalDialog(Date endInterval) {
        final Calendar calender = Calendar.getInstance();
        calender.setTime(endInterval);
        mEndTimePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            mMainPresenter.setEndIntervalDate(calender.getTime());
            mMainPresenter.hideTimeDialog();
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true);
        mEndTimePicker.setTitle(R.string.activity_main_select_time);
        mEndTimePicker.show();
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
}
