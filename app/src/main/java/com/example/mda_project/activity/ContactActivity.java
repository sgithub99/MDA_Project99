package com.example.mda_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.mda_project.R;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbarContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbarContact = findViewById(R.id.toolbarContact);
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(toolbarContact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}