package com.suzei.timescheduler;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.suzei.timescheduler.preference.SettingsActivity;
import com.suzei.timescheduler.util.AppTheme;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_list) RecyclerView listScheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTheme.set(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }

    private void initObjects() {
        ButterKnife.bind(this);
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
        FullscreenDialogEditor dialogEditor = new FullscreenDialogEditor(MainActivity.this);
        dialogEditor.show();
    }
}
