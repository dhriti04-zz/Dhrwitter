package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView tvChars;
    private final int COMPOSE_RESULT_CODE = 20;
    TextWatcher tw;
    Boolean isLong;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        btnTweet = (Button) findViewById(R.id.btnTweet);
        etText = (EditText) findViewById(R.id.etTweet);
        tvChars = (TextView) findViewById(R.id.tvChars);

        client = TwitterApp.getRestClient();

        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etText.length() > 140) {
                    String str = "Your tweet is " + (etText.length()-140) + " characters longer than required!";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                    isLong = true;
                }
                else {
                    String str = (140- etText.length()) + "";
                    tvChars.setText(str);
//                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                    isLong = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etText.addTextChangedListener(tw);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isLong) {
                    client.sendTweet(etText.getText().toString(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            Tweet tweet = null;
                            try {
                                tweet = Tweet.fromJSON(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                                Intent i = new Intent();
                                i.putExtra("Tweet", tweet);
                                setResult(COMPOSE_RESULT_CODE, i);
                                finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
                            Log.d("TwitterClient", "not working?");
                        }
                    });
                } else {
                    System.out.println("too long dude");
                }
            }
        });
    }



    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        finish();
    }
}
