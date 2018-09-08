package com.codequest.kitchan.countdowntimer.features.history.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codequest.kitchan.countdowntimer.R;
import com.codequest.kitchan.countdowntimer.features.history.adapter.TimerTaskHistoryAdapter;
import com.codequest.kitchan.countdowntimer.models.TimerTask;
import com.codequest.kitchan.countdowntimer.sharedPreferences.MyPrefs_;
import com.codequest.kitchan.countdowntimer.utils.HistoryHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Collections;
import java.util.List;

@EActivity(R.layout.activity_history)
public class HistoryActivity extends AppCompatActivity {

    @ViewById(R.id.rv_timertask)
    RecyclerView rvTimerTask;

    @Pref
    MyPrefs_ myPrefs;

    @AfterViews
    void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        HistoryHelper historyHelper = new HistoryHelper(myPrefs);

        List<TimerTask> itemsData = historyHelper.getTimerRecords();
        Collections.reverse(itemsData); // Put the latest record on the top

        rvTimerTask.setLayoutManager(new LinearLayoutManager(this));
        TimerTaskHistoryAdapter mAdapter = new TimerTaskHistoryAdapter(this, itemsData.toArray(new TimerTask[itemsData.size()]));
        rvTimerTask.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(
                rvTimerTask.getContext(),
                DividerItemDecoration.VERTICAL
        );
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider));
        rvTimerTask.addItemDecoration(divider);
    }

}

