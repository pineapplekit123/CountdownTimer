package com.codequest.kitchan.countdowntimer.utils;

import android.text.TextUtils;

import com.codequest.kitchan.countdowntimer.models.TimerTask;
import com.codequest.kitchan.countdowntimer.sharedPreferences.MyPrefs_;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

    private static final String TAG = HistoryHelper.class.getSimpleName();

    private MyPrefs_ myPrefs;
    private Gson gson;

    public HistoryHelper(MyPrefs_ myPrefs) {
        this.myPrefs = myPrefs;
        this.gson = new Gson();
    }

    public void addTimerRecord(TimerTask task) {

        List<TimerTask> timerTaskList = getTimerRecords();

        timerTaskList.add(task);
        String timerHistoryListJsonString = gson.toJson(timerTaskList);
        myPrefs.timerTaskHistory().put(timerHistoryListJsonString);

    }

    public List<TimerTask> getTimerRecords() {

        List<TimerTask> timerTaskList = new ArrayList<>();

        String timerHistoryListJsonString = myPrefs.timerTaskHistory().get();

        if (!TextUtils.isEmpty(timerHistoryListJsonString)) {
            timerTaskList = gson.fromJson(timerHistoryListJsonString, new TypeToken<List<TimerTask>>() {
            }.getType());
        }

        return timerTaskList;
    }

    public void clearAll() {
        myPrefs.timerTaskHistory().remove();
    }
}
