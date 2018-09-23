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
import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.view.EditScheduleDialog;

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
        viewHolder.bind(schedule);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title) TextView titleView;
        @BindView(R.id.item_subtitle1) TextView timeView;
        @BindView(R.id.item_subtitle2) TextView dayView;
        @BindView(R.id.item_switch) Switch switchView;
        @BindView(R.id.item_delete) ImageButton deleteView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Schedule schedule) {
            titleView.setText(schedule.getName());
            timeView.setText(schedule.getStartTime());
            dayView.setText(getDay(schedule.getDay()));
            setItemClickListener(schedule);
            setSwitchListener(schedule.getActive());
            setDeleteClickListener(schedule.get_id());
        }

        private void setItemClickListener(Schedule schedule) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    EditScheduleDialog editDialog = new EditScheduleDialog(activity);
                    editDialog.setName(schedule.getName());
                    editDialog.setTime(schedule.getStartTime());
                    editDialog.show();
                }

            });
        }

        private void setDeleteClickListener(long id) {
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setSwitchListener(int active) {
            if (active == TimeSchedulerContract.TimeScheduleEntry.TRUE) {
                switchView.setChecked(true);
            }

            switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {
                    Toast.makeText(itemView.getContext(), "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(itemView.getContext(), "Not Checked", Toast.LENGTH_SHORT).show();
                }

            });
        }

        private String getDay(Day day) {
            StringBuilder builder = new StringBuilder();
            if (isActive(day.getSunday())) {
                builder.append("S ");
            }

            if (isActive(day.getMonday())) {
                builder.append("M ");
            }

            if (isActive(day.getTuesday())) {
                builder.append("T ");
            }

            if (isActive(day.getWednesday())) {
                builder.append("W ");
            }

            if (isActive(day.getThursday())) {
                builder.append("T ");
            }

            if (isActive(day.getFriday())) {
                builder.append("F ");
            }

            if (isActive(day.getSaturday())) {
                builder.append("S");
            }

            return builder.toString();
        }

        private boolean isActive(int day) {
            return day == TimeSchedulerContract.TimeScheduleEntry.TRUE;
        }
    }

}
