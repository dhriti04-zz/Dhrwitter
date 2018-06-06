package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComposeActivity extends AppCompatActivity {

    Button btnTweet;
    TwitterClient client;
    EditText etText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        btnTweet = (Button) findViewById(R.id.btnTweet);
        etText = (EditText) findViewById(R.id.etTweet);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSubmit(v);
            }
        });
    }



    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
