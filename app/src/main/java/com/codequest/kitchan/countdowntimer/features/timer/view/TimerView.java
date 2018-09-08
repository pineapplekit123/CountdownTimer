package com.codequest.kitchan.countdowntimer.features.timer.view;

import com.codequest.kitchan.countdowntimer.models.TimerTask;

public interface TimerView {
    void updateUIButton(boolean timerStarted);
    void updateTimerText(long millisUntilFinished);
    void storeTask(TimerTask timerTask);
}
