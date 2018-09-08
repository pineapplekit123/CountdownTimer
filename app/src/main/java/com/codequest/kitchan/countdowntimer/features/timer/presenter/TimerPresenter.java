package com.codequest.kitchan.countdowntimer.features.timer.presenter;

public interface TimerPresenter {
    void startTimer(String taskname, long millisInFuture, long countDownInterval);
    void stopTimer();
}

