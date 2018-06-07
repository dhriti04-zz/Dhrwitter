package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    Button btnTweet;
    TwitterClient client;
    EditText etText;
    private final int COMPOSE_RESULT_CODE = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        btnTweet = (Button) findViewById(R.id.btnTweet);
        etText = (EditText) findViewById(R.id.etTweet);
        client = TwitterApp.getRestClient();

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.sendTweet(etText.getText().toString(), new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        Tweet tweet = null;
                        try {
                            tweet = Tweet.fromJSON(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (etText.length() > 140) {
                            String s = "Your tweet is " + (etText.length()-140) + " characters longer than required!";
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                        }
                        else if (etText.length() <= 140) {
                            Intent i = new Intent();
                            i.putExtra("Tweet", tweet);
                            setResult(COMPOSE_RESULT_CODE, i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("TwitterClient", "not working?");
                    }
                });


//                  onSubmit(v);
            }
        });
    }



    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        finish();
    }
}
