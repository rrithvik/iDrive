package com.rrithvik.idrive;

import android.os.CountDownTimer;

import static com.rrithvik.idrive.MainActivity.startTime;

/**
 * Created by rrithvik on 11/27/17.
 */

public class CountDownTimerTest extends CountDownTimer {

    public String timeElapsed, timeElapsedView;
    public CountDownTimerTest(long startTime, long interval) {
        super(startTime, interval);
    }

    @Override
    public void onFinish() {
        timeElapsedView= ("Time Elapsed: " + String.valueOf(startTime));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeElapsed = String.valueOf(startTime - millisUntilFinished);
        timeElapsedView = ("Time Elapsed: " + String.valueOf(timeElapsed));
    }
}
