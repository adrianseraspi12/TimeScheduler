package com.suzei.timescheduler.preference;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceManager;

import com.suzei.timescheduler.BuildConfig;
import com.suzei.timescheduler.R;

public class SettingsFragment extends BasePreferenceFragmentCompat implements
        Preference.OnPreferenceClickListener {

    private Preference themePref;
    private Preference donatePref;
    private Preference feedBackPref;
    private Preference moreFromDevPref;
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
        preferenceCategory = (PreferenceCategory) findPreference("general");
    }

    private void setClickListener() {
        themePref.setOnPreferenceClickListener(this);
        donatePref.setOnPreferenceClickListener(this);
        feedBackPref.setOnPreferenceClickListener(this);
        moreFromDevPref.setOnPreferenceClickListener(this);
        themePref.setOnPreferenceChangeListener(summaryToValue);
        summaryToValue.onPreferenceChange(themePref, PreferenceManager
                .getDefaultSharedPreferences(themePref.getContext())
                .getString(themePref.getKey(), "Default Theme"));
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
}
