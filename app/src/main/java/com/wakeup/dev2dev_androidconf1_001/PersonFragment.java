package com.wakeup.dev2dev_androidconf1_001;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonFragment extends Fragment {
    private static final String TAG = PersonFragment.class.getSimpleName();
    private static final String KEY_PERSON = "person";

    private ImageView portraitView;
    private TextView nameView;
    private TextView genderView;
    private TextView birthView;
    private TextView infoView;

    private Person person;

    public static PersonFragment newInstance(Person person) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PERSON, person);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person = (Person) getArguments().getSerializable(KEY_PERSON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        portraitView = (ImageView) view.findViewById(R.id.portrait);
        nameView = (TextView) view.findViewById(R.id.name);
        genderView = (TextView) view.findViewById(R.id.gender);
        birthView = (TextView) view.findViewById(R.id.birth_date);
        infoView = (TextView) view.findViewById(R.id.info);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (person != null) {
            nameView.setText(person.getName());
            genderView.setText(person.getGender());
            birthView.setText(person.getBirthYear());

            String info = getString(R.string.info_format, person.getMass(), person.getHeight(),
                    person.getEyeColor());
            infoView.setText(info);


            try {
                int color = Color.parseColor(person.getEyeColor());
                portraitView.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "onCreate: ", e);
            }
        }
    }
}
