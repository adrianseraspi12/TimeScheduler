package com.suzei.timescheduler.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.TimeSchedule;
import com.suzei.timescheduler.adapter.ScheduleAdapter;
import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.preference.SettingsActivity;
import com.suzei.timescheduler.util.AppTheme;
import com.suzei.timescheduler.viewmodel.DeleteScheduleViewModel;
import com.suzei.timescheduler.viewmodel.ScheduleCollectionViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private ScheduleAdapter adapter;
    private List<Schedule> scheduleList;

    private int tempPosition;
    private Schedule tempSchedule;

    @Inject
    ViewModelProvider.Factory viewModelProvider;

    private ScheduleCollectionViewModel scheduleCollectionViewModel;
    private DeleteScheduleViewModel deleteScheduleViewModel;

    @BindString(R.string.undo) String undoString;
    @BindString(R.string.schedule_deleted) String scheduleDeleteString;
    @BindView(R.id.main_root_view) CoordinatorLayout rootView;
    @BindView(R.id.main_list) RecyclerView listScheduleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTheme.set(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        setUpDagger();
        setUpViewModel();
        setUpScheduleList();
        setUpRecyclerView();
    }

    private void initObjects() {
        ButterKnife.bind(this);
    }

    private void setUpDagger() {
        ((TimeSchedule) getApplication())
                .getAppComponent()
                .inject(this);
    }

    private void setUpViewModel() {
        scheduleCollectionViewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(ScheduleCollectionViewModel.class);

        deleteScheduleViewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(DeleteScheduleViewModel.class);
    }

    private void setUpScheduleList() {
        scheduleCollectionViewModel.getAllSchedule().observe(this, schedules -> {
            if (MainActivity.this.scheduleList == null) {
                setListToAdapter(schedules);
            }
        });
    }

    private void setUpRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        listScheduleView.setLayoutManager(llm);
        listScheduleView.setHasFixedSize(true);
        listScheduleView.addItemDecoration(new DividerItemDecoration(
                MainActivity.this, llm.getOrientation()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helperCallback());
        itemTouchHelper.attachToRecyclerView(listScheduleView);
    }

    private void setListToAdapter(List<Schedule> schedules) {
        this.scheduleList = schedules;
        adapter = new ScheduleAdapter(this, schedules);
        listScheduleView.setAdapter(adapter);
    }

    private ItemTouchHelper.Callback helperCallback() {

        return new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                tempPosition = position;
                tempSchedule = scheduleList.get(position);

                scheduleList.remove(scheduleList.get(position));
                adapter.notifyItemRemoved(position);

                showUndoSnackbar();
            }
        };
    }

    private void showUndoSnackbar() {
        Snackbar.make(rootView, scheduleDeleteString, Snackbar.LENGTH_LONG)
                .setAction(undoString, v -> {
                    scheduleList.add(tempSchedule);
                    adapter.notifyItemInserted(tempPosition);

                    this.tempSchedule = null;
                    this.tempPosition = 0;
                })
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (tempSchedule != null) {
                            deleteScheduleViewModel.deleteSchedule(tempSchedule);
                        }
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.main_fab)
    public void onAddClick() {
        startActivity(new Intent(this, CreateActivity.class));
    }
}
