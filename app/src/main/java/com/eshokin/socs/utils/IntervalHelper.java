package com.eshokin.socs.utils;

import com.eshokin.socs.R;
import com.eshokin.socs.api.enumerations.Interval;

import java.util.TreeMap;

import static com.eshokin.socs.api.enumerations.Interval.D1;
import static com.eshokin.socs.api.enumerations.Interval.H1;
import static com.eshokin.socs.api.enumerations.Interval.H12;
import static com.eshokin.socs.api.enumerations.Interval.H4;
import static com.eshokin.socs.api.enumerations.Interval.M1;
import static com.eshokin.socs.api.enumerations.Interval.M15;
import static com.eshokin.socs.api.enumerations.Interval.M30;
import static com.eshokin.socs.api.enumerations.Interval.M5;

public class IntervalHelper {

    private static final TreeMap<Interval, Integer> mIntervalResources = new TreeMap<Interval, Integer>() {
        {
            put(M1, R.string.activity_main_m1);
            put(M5, R.string.activity_main_m5);
            put(M15, R.string.activity_main_m15);
            put(M30, R.string.activity_main_m30);
            put(H1, R.string.activity_main_h1);
            put(H4, R.string.activity_main_h4);
            put(H12, R.string.activity_main_h12);
            put(D1, R.string.activity_main_d1);
        }
    };

    public static TreeMap<Interval, Integer> getIntervalResources() {
        return mIntervalResources;
    }
}
