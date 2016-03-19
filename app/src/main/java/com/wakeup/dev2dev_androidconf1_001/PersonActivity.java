package com.wakeup.dev2dev_androidconf1_001;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonActivity extends BaseActivity {

    public static final String EXTRA_PERSON = "ru.dev2dev.workshopinit.PERSON";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        Person person = (Person) getIntent().getSerializableExtra(EXTRA_PERSON);
        PersonFragment personFragment = (PersonFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        if (personFragment == null) {
            personFragment = PersonFragment.newInstance(person);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, personFragment)
                    .commit();
        }
    }
}
