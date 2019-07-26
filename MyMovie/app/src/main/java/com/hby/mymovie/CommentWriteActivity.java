package com.hby.mymovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CommentWriteActivity extends AppCompatActivity implements FragmentCallback2 {
    RatingBar ratingBar;
    EditText contentsInput;
    DetailFragment fragment_detail;
    Button saveButton;

    TextView titleTextView;
    TextView textView;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        textView = (TextView) findViewById(R.id.textView);

        setTitle("한줄평 작성");
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        contentsInput = (EditText) findViewById(R.id.contentsInput);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (fragment_detail != null) {
                    /*
                    String contents = contentsInput.getText().toString();
                    fragment_detail.onCommandFromActivity("comment",contents);

                    Intent intent = new Intent();
                    intent.putExtra("commentView", contents);

                    setResult(RESULT_OK, intent);

                    finish();
                    */
                    returnToMain();
                //}
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        if (intent != null) {
            float rating = intent.getFloatExtra("rating", 0.0f);
            ratingBar.setRating(rating);
        }
    }

    public void returnToMain() {
        String contents = contentsInput.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("contents", contents);

        setResult(RESULT_OK, intent);

        finish();

    }

    public void onCommand(String command, String data) {
        textView.setText(data);
    }

}
