package com.wakeup.dev2dev_androidconf1_001;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SwService extends IntentService {

    private static final String TAG = SwService.class.getSimpleName();
    public static final String ACTION_GET_PEOPLE = "ru.dev2dev.workshopinit.GET_PEOPLE";
    public static final String KEY_RESULT = "ru.dev2dev.workshopinit.RESULT";

    public SwService() {
        super("SwService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Person> people = null;
        try {
            String jsonPeople = Prefs.getPeople(this);
            if (jsonPeople == null) {
                //SystemClock.sleep(5000);
                String url = "http:/swapi.co/api/people/";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", "sw-android")
                        .build();
                Response response = client.newCall(request).execute();
                jsonPeople = response.body().string();
                Log.d(TAG, jsonPeople);
                Prefs.savePeople(this, jsonPeople);
            }
            people = Person.getPeople(jsonPeople);
        } catch (IOException ex) {
            Log.e(TAG, "doInBackgound: ", ex);
        }
        sendResult(people);
    }

    private void sendResult(ArrayList<Person> people) {
        Intent intent = new Intent(ACTION_GET_PEOPLE).putExtra(KEY_RESULT, people);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
