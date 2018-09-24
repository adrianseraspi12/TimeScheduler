package com.suzei.timescheduler.adapter;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.TimeSchedule;
import com.suzei.timescheduler.alarm.AlarmHelper;
import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.view.UpdateActivity;
import com.suzei.timescheduler.viewmodel.UpsertScheduleViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Context context;
    private List<Schedule> scheduleList;

    private UpsertScheduleViewModel upsertScheduleViewModel;

    public ScheduleAdapter(Context context,
                           List<Schedule> scheduleList,
                           UpsertScheduleViewModel viewModel) {
        this.scheduleList = scheduleList;
        this.context = context;
        this.upsertScheduleViewModel = viewModel;
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
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.item_title) TextView titleView;
        @BindView(R.id.item_subtitle1) TextView timeView;
        @BindView(R.id.item_switch) Switch switchView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            switchView.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            Schedule schedule = scheduleList.get(getAdapterPosition());
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra(UpdateActivity.EXTRA_ID, schedule.getId());
            context.startActivity(intent);
            Timber.i("Item Id= %s", String.valueOf(schedule.getId()));
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Schedule schedule = scheduleList.get(getAdapterPosition());
            AlarmHelper alarmHelper = new AlarmHelper(
                    context,
                    schedule.getName());

            //  This two should be unique because we need to run two alarmManager
            int startTimeId = Integer.parseInt(schedule.getId());
            int endTimeId = Integer.parseInt(schedule.getId()) + 1;

            if (isChecked) {
                alarmHelper.start(
                        schedule.getStartTime(),
                        schedule.getEndTime(),
                        startTimeId,
                        endTimeId);

                upsertScheduleViewModel.upsertSchedule(new Schedule(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getStartTime(),
                        schedule.getEndTime(),
                        TimeSchedule.ACTIVE
                ));
            } else {
                alarmHelper.cancel(startTimeId);
                alarmHelper.cancel(endTimeId);

                upsertScheduleViewModel.upsertSchedule(new Schedule(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getStartTime(),
                        schedule.getEndTime(),
                        TimeSchedule.NOT_ACTIVE
                ));
            }

        }
    }


}
