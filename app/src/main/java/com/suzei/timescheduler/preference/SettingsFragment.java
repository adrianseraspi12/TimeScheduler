package com.suzei.timescheduler.preference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.RingtonePreference;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceManager;

import com.suzei.timescheduler.BuildConfig;
import com.suzei.timescheduler.R;

public class SettingsFragment extends BasePreferenceFragmentCompat implements
        Preference.OnPreferenceClickListener {

    public static final int ALARM_TONE = 1;

    private Preference themePref;
    private Preference donatePref;
    private Preference feedBackPref;
    private Preference moreFromDevPref;
    private Preference alarmTonePref;
    private PreferenceCategory preferenceCategory;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference);
        showVersionName();
        initPreferences();
        setClickListener();
        hideDonateifThemesUnlock();
    }

    private void showVersionName() {
        String appVersion = BuildConfig.VERSION_NAME;
        Preference appVersionPref = findPreference("app_version");
        appVersionPref.setSummary(appVersion);
    }

    private void initPreferences() {
        themePref = findPreference("app_theme");
        donatePref = findPreference("premium_account");
        feedBackPref = findPreference("send_feedback");
        moreFromDevPref = findPreference("more_from_dev");
        alarmTonePref = findPreference("alarm_tone");
        preferenceCategory = (PreferenceCategory) findPreference("general");
    }

    private void setClickListener() {
        themePref.setOnPreferenceClickListener(this);
        donatePref.setOnPreferenceClickListener(this);
        feedBackPref.setOnPreferenceClickListener(this);
        moreFromDevPref.setOnPreferenceClickListener(this);
        alarmTonePref.setOnPreferenceClickListener(this);
        themePref.setOnPreferenceChangeListener(summaryToValue);
        alarmTonePref.setOnPreferenceChangeListener(summaryToValue);

        summaryToValue.onPreferenceChange(themePref, PreferenceManager
                .getDefaultSharedPreferences(themePref.getContext())
                .getString(themePref.getKey(), "Default Theme"));
        summaryToValue.onPreferenceChange(alarmTonePref, PreferenceManager
                .getDefaultSharedPreferences(alarmTonePref.getContext())
                .getString(alarmTonePref.getKey(), "Silent"));
    }

    private void hideDonateifThemesUnlock() {
        boolean isPremium = PreferenceManager
                .getDefaultSharedPreferences(donatePref.getContext())
                .getBoolean("premium_account", false);

        if (isPremium) {
            preferenceCategory.removePreference(donatePref);
        }
    }

    private Preference.OnPreferenceChangeListener summaryToValue = (preference, newVal) -> {
        String stringValue = newVal.toString();
        if (preference.getKey().equals("app_theme")) {

            preference.setSummary(stringValue);

        } else if (preference.getKey().equals("alarm_tone")) {
            setAlarmToneSummary(Uri.parse(stringValue));
        }
        return true;
    };

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        switch (key) {
            case "app_theme":
                ListThemeDialog dialog = new ListThemeDialog(getContext(), theme ->
                        getActivity().recreate());
                dialog.show();
                return true;

            case "premium_account":
                //  TODO: In App purchase
                return true;

            case "alarm_tone":
                ringtoneList();
                return true;

            case "send_feedback":
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);

                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL,
                        new String[] {"adrianseraspi12@gmail.com"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
                return true;

            case "more_from_dev":
                final String developerId = "developer?id=Suzei";
                try {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://" + developerId)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/" + developerId)));
                }

                return true;

            default:
                throw new IllegalArgumentException("Invalid Preference key=" + key);
        }
    }

    private void ringtoneList() {
        Intent i = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        i.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        i.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        i.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        i.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                Settings.System.DEFAULT_NOTIFICATION_URI);

        String existingValue = PreferenceManager
                .getDefaultSharedPreferences(alarmTonePref.getContext())
                .getString(alarmTonePref.getKey(), "");

        if (existingValue.length() == 0) {
            // Select "Silent"
            i.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        } else {
            i.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(existingValue));
        }

        startActivityForResult(i, ALARM_TONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALARM_TONE && data != null) {
            Uri ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (ringtone != null) {
                editPreference(alarmTonePref, alarmTonePref.getKey(), ringtone.toString());
                setAlarmToneSummary(ringtone);
            } else {
                // "Silent" was selected
                editPreference(alarmTonePref, alarmTonePref.getKey(), "");
                setAlarmToneSummary(ringtone);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void editPreference(Preference preference, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void setAlarmToneSummary(Uri uri) {
        Ringtone ringtoneTitle = RingtoneManager.getRingtone(alarmTonePref.getContext(), uri);
        alarmTonePref.setSummary(ringtoneTitle.getTitle(alarmTonePref.getContext()));
    }
}
