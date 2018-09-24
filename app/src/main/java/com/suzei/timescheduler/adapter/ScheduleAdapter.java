package com.suzei.timescheduler.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.TimeSchedule;
import com.suzei.timescheduler.database.ScheduleEntity;
import com.suzei.timescheduler.view.UpdateActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Activity activity;
    private List<ScheduleEntity> scheduleEntityList;

    public ScheduleAdapter(Activity activity, List<ScheduleEntity> scheduleEntityList) {
        this.scheduleEntityList = scheduleEntityList;
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
        ScheduleEntity scheduleEntity = scheduleEntityList.get(i);
        String time = scheduleEntity.getStartTime() + "-" + scheduleEntity.getEndTime();

        viewHolder.titleView.setText(scheduleEntity.getName());
        viewHolder.timeView.setText(time);

        if (scheduleEntity.getActive() == TimeSchedule.ACTIVE) {
            viewHolder.switchView.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return scheduleEntityList.size();
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
            ScheduleEntity scheduleEntity = scheduleEntityList.get(getAdapterPosition());
            Intent intent = new Intent(activity, UpdateActivity.class);
            intent.putExtra(UpdateActivity.EXTRA_ID, scheduleEntity.getId());
            activity.startActivity(intent);
            Timber.i("Item Id= %s", String.valueOf(scheduleEntity.getId()));
        }
    }

}
