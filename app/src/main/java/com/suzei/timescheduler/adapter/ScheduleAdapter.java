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
            setItemClickListener(schedule);
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
    }

}
