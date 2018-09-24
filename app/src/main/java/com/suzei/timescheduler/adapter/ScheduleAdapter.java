package com.suzei.timescheduler.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.TimeSchedule;
import com.suzei.timescheduler.database.Schedule;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Activity activity;
    private List<Schedule> scheduleList;

    public ScheduleAdapter(Activity activity, List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_schedules,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Schedule schedule = scheduleList.get(i);
        String time = schedule.getStartTime() + "-" + schedule.getEndTime();

        viewHolder.titleView.setText(schedule.getName());
        viewHolder.timeView.setText(time);

        if (schedule.getActive() == TimeSchedule.ACTIVE) {
            viewHolder.switchView.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_title) TextView titleView;
        @BindView(R.id.item_subtitle1) TextView timeView;
        @BindView(R.id.item_switch) Switch switchView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Schedule schedule = scheduleList.get(getAdapterPosition());

            Toast.makeText(activity.getApplicationContext(),
                    String.valueOf(schedule.getId()),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
