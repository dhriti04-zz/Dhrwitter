package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String name;
    public long uid;    //database ID for tweet
    public String screenName;
    public String profileImageUrl;

    //deserialize the JSON
    public static User fromJSON(JSONObject json) throws JSONException {

        User user = new User();

        //extract and fill values;

        user.name = json.getString("name");
        user.screenName = json.getString("screen_name");
        user.uid = json.getLong("id");
        user.profileImageUrl = json.getString("profile_image_url");


        return user;


    }
}
