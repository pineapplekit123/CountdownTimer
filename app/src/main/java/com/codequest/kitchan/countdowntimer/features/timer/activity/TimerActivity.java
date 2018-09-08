package com.codequest.kitchan.countdowntimer.features.timer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codequest.kitchan.countdowntimer.R;
import com.codequest.kitchan.countdowntimer.features.history.activity.HistoryActivity_;
import com.codequest.kitchan.countdowntimer.features.timer.PersonalInfoDialog;
import com.codequest.kitchan.countdowntimer.features.timer.presenter.TimerPresenterImpl;
import com.codequest.kitchan.countdowntimer.features.timer.view.TimerView;
import com.codequest.kitchan.countdowntimer.models.TimerTask;
import com.codequest.kitchan.countdowntimer.sharedPreferences.MyPrefs_;
import com.codequest.kitchan.countdowntimer.utils.HistoryHelper;
import com.codequest.kitchan.countdowntimer.utils.StringUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EActivity(R.layout.activity_timer)
public class TimerActivity extends AppCompatActivity implements TimerView {

    private final String TAG = TimerActivity.class.getSimpleName();

    @ViewById(R.id.et_taskname)
    EditText etTaskname;

    @ViewById(R.id.cl_parentView)
    ConstraintLayout parentLayout;

    @ViewById(R.id.iv_startstop)
    ImageView ivStartStop;

    @ViewById(R.id.tv_timer)
    TextView tvTimer;

    @ViewsById({R.id.iv_timer_decrement, R.id.iv_timer_increment})
    List<ImageView> ivTimercalibrators;

    @ViewById(R.id.iv_startstop)
    ImageView ivTimerStartStop;

    @Pref
    MyPrefs_ myPrefs;

    private boolean timerisStarted;
    private TimerPresenterImpl presenter;
    private static final long timerMaxCountInMilliSeconds = 10 * 1000; // 25:00:00 hh:mm:ss
    private long timeCountInMilliSeconds = timerMaxCountInMilliSeconds;
    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    private final long REPEAT_DELAY = 50;
    private HistoryHelper historyHelper;

    @AfterViews
    protected void init() {
        presenter = new TimerPresenterImpl(this);
        updateTimerText(timeCountInMilliSeconds);
        historyHelper = new HistoryHelper(myPrefs);

    }

    @Override
    public void updateUIButton(boolean timerStarted) {
        Log.d(TAG, "timer is on?:" + timerStarted);

        timerisStarted = timerStarted;

        Drawable startstopImg = timerStarted
                ? ContextCompat.getDrawable(this, android.R.drawable.ic_menu_close_clear_cancel)
                : ContextCompat.getDrawable(this, android.R.drawable.ic_media_play);

        for (ImageView ivView : ivTimercalibrators) {
            ivView.setVisibility(timerStarted ? View.GONE : View.VISIBLE);
        }

        ivStartStop.setImageDrawable(startstopImg);
        etTaskname.setText(!timerStarted ? "" : etTaskname.getText());
        etTaskname.setFocusable(!timerStarted);
        etTaskname.setClickable(!timerStarted);
        etTaskname.setEnabled(!timerStarted);
        etTaskname.setFocusableInTouchMode(!timerStarted);

        updateTimerText(timeCountInMilliSeconds);
    }

    @Override
    public void updateTimerText(long millisUntilFinished) {
        tvTimer.setText(StringUtils.hmsTimeFormatter(millisUntilFinished));
    }

    @Override
    public void storeTask(TimerTask timerTask) {
        historyHelper.addTimerRecord(timerTask);
    }

    @Click(R.id.iv_startstop)
    protected void ivStartStopClicked() {
        if (!timerisStarted) {

            String usrInputTaskName = etTaskname.getText().toString();
            String taskname = TextUtils.isEmpty(usrInputTaskName) ? getResources().getString(R.string.input_taskname_default) : usrInputTaskName;

            /* Known issue
                CountdownTimer - if the time remaining is less than the interval,
                it will not call OnTick() and just wait until finish
                so workaround is lower the time intervals,
                or we can implement our own countdowntimer class to override that handling method.

                **** the above issue is modified in Oreo ****
            */
            presenter.startTimer(taskname, timeCountInMilliSeconds, 500);
        } else {
            presenter.stopTimer();
        }
    }

    @Click(R.id.iv_info)
    protected void ivInfoClicked() {
        PersonalInfoDialog alert = new PersonalInfoDialog();
        alert.showDialog(this);
    }


    @Click(R.id.iv_history)
    protected void ivHistoryClicked() {
        Intent intent = new Intent(this, HistoryActivity_.class);
        startActivity(intent);
    }

    @Click({R.id.iv_timer_increment, R.id.iv_timer_decrement})
    protected void onclick_timercalibraetor(View v) {
        switch (v.getId()) {
            case R.id.iv_timer_increment:
                increment();
                break;
            case R.id.iv_timer_decrement:
                decrement();
                break;
            default:
                break;
        }
    }

    @LongClick({R.id.iv_timer_increment, R.id.iv_timer_decrement})
    protected boolean onLongClick_timercalibraetor(View v) {

        switch (v.getId()) {
            case R.id.iv_timer_increment:
                mAutoIncrement = true;
                break;
            case R.id.iv_timer_decrement:
                mAutoDecrement = true;
                break;
            default:
                break;
        }

        repeatUpdateHandler.post(new RptUpdater());

        return false;
    }

    @Touch({R.id.iv_timer_increment, R.id.iv_timer_decrement})
    protected boolean onTouch_timercalibraete(View v, MotionEvent event) {

        switch (v.getId()) {
            case R.id.iv_timer_increment:
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement) {
                    mAutoIncrement = false;
                }
                break;
            case R.id.iv_timer_decrement:
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement) {
                    mAutoDecrement = false;
                }
                break;
            default:
                break;
        }

        return false;
    }

    @FocusChange(R.id.cl_parentView)
    protected void onTouch_parentView(View v, boolean hasFocus) {
        if (hasFocus) {
            hideSoftKeyboard(TimerActivity.this);
        }
    }

    private void increment() {
        timeCountInMilliSeconds += 1000;
        updateTimerText(timeCountInMilliSeconds);
    }

    private void decrement() {
        if (timeCountInMilliSeconds >= 1000) { // prevent timeCountInMilliSeconds become negative value
            timeCountInMilliSeconds -= 1000;
            updateTimerText(timeCountInMilliSeconds);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    class RptUpdater implements Runnable {
        public void run() {
            if (mAutoIncrement) {
                increment();
                repeatUpdateHandler.postDelayed(new RptUpdater(), REPEAT_DELAY);
            } else if (mAutoDecrement) {
                decrement();
                repeatUpdateHandler.postDelayed(new RptUpdater(), REPEAT_DELAY);
            }
        }
    }
}

