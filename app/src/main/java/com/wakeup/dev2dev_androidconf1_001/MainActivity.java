package com.wakeup.dev2dev_androidconf1_001;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private static final String KEY_POSITION = "position";

    private ProgressBar progressBar;
    private ListView listView;
    private boolean twoPane;
    private int postition = ListView.INVALID_POSITION;

    private BroadcastReceiver peopleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Person> people = (ArrayList<Person>) intent.getSerializableExtra(SwService.KEY_RESULT);
            showPeople(people);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPerson(position);
            }
        });

        if (findViewById(R.id.person_container) != null) {
            twoPane = true;
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        }

        if (savedInstanceState != null) {
            postition = savedInstanceState.getInt(KEY_POSITION, ListView.INVALID_POSITION);
        }

        showProgress(true);

        Intent intent = new Intent(this, SwService.class);
        startService(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_POSITION, postition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(SwService.ACTION_GET_PEOPLE);
        LocalBroadcastManager.getInstance(this).registerReceiver(peopleReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(peopleReceiver);
        super.onStop();
    }

    private void showPerson(int position) {
        this.postition = position;
        Person person = (Person) listView.getAdapter().getItem(position);
        if (twoPane) {
            PersonFragment personFragment = PersonFragment.newInstance(person);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.person_container, personFragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, PersonActivity.class).putExtra(PersonActivity.EXTRA_PERSON, person);
            startActivity(intent);
        }
    }

    private void showProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
    }

    private void showPeople(ArrayList<Person> people) {
        showProgress(false);
        if (people != null) {
            ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_activated_1, people);
            listView.setAdapter(adapter);
        }

        if (postition != ListView.INVALID_POSITION) {
            showPerson(postition);
            listView.setSelection(postition);
            listView.setItemChecked(postition, true);
        }
    }
}
