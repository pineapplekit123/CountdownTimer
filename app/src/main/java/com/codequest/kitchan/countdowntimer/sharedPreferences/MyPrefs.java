package com.codequest.kitchan.countdowntimer.sharedPreferences;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    @DefaultString("")
    String timerTaskHistory(); //JSON String, parse from List<TimerTask>

}
