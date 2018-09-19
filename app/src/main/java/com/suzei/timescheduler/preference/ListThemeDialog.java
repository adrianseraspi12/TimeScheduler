package com.suzei.timescheduler.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suzei.timescheduler.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListThemeDialog extends Dialog {

    @BindView(R.id.dialog_list) RecyclerView listThemeView;

    private Callback callback;

    private List<String> textList = new ArrayList<>();
    private List<Integer> imageList = new ArrayList<>();

    ListThemeDialog(@NonNull Context context, Callback _callback) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.callback = _callback;
        setContentView(R.layout.dialog_list);
        ButterKnife.bind(this, this);
        addItems();
        initRecyclerView();
    }

    private void addItems() {
        textList.add("Default Theme");
        textList.add("Dark Theme");
        textList.add("Green Theme");
        textList.add("Green Dark Theme");
        textList.add("Pink Theme");
        textList.add("Pink Dark Theme");

        imageList.add(R.drawable.default_theme);
        imageList.add(R.drawable.default_dark_theme);
        imageList.add(R.drawable.green_light_theme);
        imageList.add(R.drawable.green_dark_theme);
        imageList.add(R.drawable.pink_light_theme);
        imageList.add(R.drawable.pink_dark_theme);
    }

    private void initRecyclerView() {
        listThemeView.setLayoutManager(new LinearLayoutManager(getContext()));
        listThemeView.setHasFixedSize(true);
        listThemeView.setAdapter(new ThemeAdapter(imageList, textList));
    }

    class ThemeAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Integer> themeImage;
        private List<String> themeText;

        ThemeAdapter(List<Integer> themeImage, List<String> themeText) {
            this.themeImage = themeImage;
            this.themeText = themeText;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.row_item_theme_selector, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String text = themeText.get(i);
            int image = themeImage.get(i);

            if (i == 0) {
                viewHolder.bind(text, image, true);
            } else {
                viewHolder.bind(text, image, isPurchased());
            }

        }

        @Override
        public int getItemCount() {
            return themeImage.size();
        }

        private boolean isPurchased() {
            return PreferenceManager
                    .getDefaultSharedPreferences(getContext())
                    .getBoolean("premium_account", false);
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.theme_image) ImageView themeImage;
        @BindView(R.id.theme_text) TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final String text, int image, boolean isPurchased) {
            textView.setText(text);
            themeImage.setImageResource(image);

            if (!isPurchased) {
                itemView.setAlpha(0.5f);
                return;
            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = PreferenceManager
                            .getDefaultSharedPreferences(getContext()).edit();
                    editor.putString("app_theme", text);
                    editor.apply();
                    callback.onItemClick(text);
                    dismiss();
                }

            });
        }

    }

    interface Callback {

        void onItemClick(String theme);

    }
}
