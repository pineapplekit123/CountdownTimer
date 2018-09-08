package com.codequest.kitchan.countdowntimer.features.history.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codequest.kitchan.countdowntimer.R;
import com.codequest.kitchan.countdowntimer.models.TimerTask;
import com.codequest.kitchan.countdowntimer.utils.StringUtils;

public class TimerTaskHistoryAdapter extends RecyclerView.Adapter<TimerTaskHistoryAdapter.ViewHolder> {
    private TimerTask[] itemsData;
    private Context context;

    public TimerTaskHistoryAdapter(Context context, TimerTask[] itemsData) {
        this.context = context;
        this.itemsData = itemsData;
    }

    @Override
    public TimerTaskHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timer_history_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        int rowColorInt = (position % 2) == 0 ? ContextCompat.getColor(context, R.color.list_item1)
                : ContextCompat.getColor(context, R.color.list_item2);

        ColorDrawable rollColor = new ColorDrawable(rowColorInt);

        viewHolder.tvTaskname.setText(itemsData[position].getName());
        viewHolder.tvCreationTime.setText(StringUtils.longToStr(itemsData[position].getMillisCreateTime(), StringUtils.date_fmt));
        viewHolder.tvDuration.setText(StringUtils.hmsTimeFormatter(itemsData[position].getTaskDurationmillis()));

        viewHolder.rlMain.setBackground(rollColor);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTaskname, tvCreationTime, tvDuration;
        RelativeLayout rlMain;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            rlMain = itemLayoutView.findViewById(R.id.rl_main);
            tvTaskname = itemLayoutView.findViewById(R.id.tv_taskname);
            tvCreationTime = itemLayoutView.findViewById(R.id.tv_taskcreatedTime);
            tvDuration = itemLayoutView.findViewById(R.id.tv_duration);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}

