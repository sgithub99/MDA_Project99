package com.example.mda_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mda_project.R;

import androidx.appcompat.app.ActionBar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class Setting extends AppCompatActivity {

    Toolbar toolbarSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        loadLocale();
        toolbarSetting = findViewById(R.id.toolbarSetting);
        actionBarSetting();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));


        Button changeLanguage = findViewById(R.id.btnChangeLanguage);

        changeLanguage.setOnClickListener(v -> showChangeLanguageDialog());


    }

    private void actionBarSetting() {
        setSupportActionBar(toolbarSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSetting.setNavigationOnClickListener(v -> finish());
    }

    private void showChangeLanguageDialog() {
        final String[] listLang = {"English", "Vietnamese"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
        builder.setTitle("Choose language");
        builder.setSingleChoiceItems(listLang, -1, (dialog, which) -> {
            if (which == 0) {
                setLocate("en");
                recreate();
            } else if (which == 1) {
                setLocate("vi");
                recreate();
            }
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocate(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My lang", language);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My lang", "");
        setLocate(language);
    }
}