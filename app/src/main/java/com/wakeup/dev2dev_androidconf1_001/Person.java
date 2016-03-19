package com.wakeup.dev2dev_androidconf1_001;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.*;

/**
 * Created by Евгения on 19.03.2016.
 */
public class Person implements Serializable {
    private String name;
    private String mass;
    private String height;
    private String birthYear;
    private String eyeColor;
    private String gender;

    public String getName() {
        return name;
    }

    public String getMass() {
        return mass;
    }

    public String getHeight() {
        return height;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        /*return "Person{" +
                "name='" + name + '\'' +
                '}';*/
        return name;
    }

    public static ArrayList<Person> getPeople(String json) {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(json).getAsJsonObject().getAsJsonArray("results");
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type collectionType = new TypeToken<ArrayList<Person>>(){}.getType();
        return gson.fromJson(jsonArray, collectionType);
    }
}
