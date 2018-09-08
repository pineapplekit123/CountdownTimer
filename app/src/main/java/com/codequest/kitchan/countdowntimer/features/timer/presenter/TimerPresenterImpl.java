package com.codequest.kitchan.countdowntimer.features.timer.presenter;

import android.os.CountDownTimer;
import android.util.Log;

import com.codequest.kitchan.countdowntimer.features.timer.view.TimerView;
import com.codequest.kitchan.countdowntimer.models.TimerTask;

public class TimerPresenterImpl implements TimerPresenter {

    private static final String TAG = TimerPresenterImpl.class.getSimpleName();

    private TimerView view;
    private CountDownTimer countDownTimer;
    private TimerTask timerTask = new TimerTask.Builder().build();
    private boolean timerStarted = false;

    public TimerPresenterImpl(TimerView view) {
        this.view = view;
    }

    @Override
    public void startTimer(String taskname, final long millisInFuture, long countDownInterval) {

        timerTask = new TimerTask.Builder()
                .name(taskname)
                .millisInFuture(millisInFuture)
                .build();

        timerStarted = true;

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long l) {
                timerTask.setMillisUntilFinished(l);
                view.updateTimerText(l);
            }

            @Override
            public void onFinish() {
                Log.d("TAG3", Long.toString(timerTask.getTaskDurationmillis()));
                timerStarted = false;
                view.updateUIButton(timerStarted);
                storeTimerTask(timerTask);
            }
        }.start();

        view.updateUIButton(timerStarted);
    }

    @Override
    public void stopTimer() {
        countDownTimer.cancel();
        timerStarted = false;
        view.updateUIButton(timerStarted);
        storeTimerTask(timerTask);
        Log.d("TAG1", Long.toString(timerTask.getTaskDurationmillis()));
    }

    private void storeTimerTask(TimerTask timerTask) {
        view.storeTask(timerTask);
    }
}
