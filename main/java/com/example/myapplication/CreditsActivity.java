package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreditsActivity extends Activity implements View.OnClickListener{

    TextView header, line1, line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.back_from_credits).setOnClickListener(this);
        header = findViewById(R.id.credits);
        line1 = findViewById(R.id.font_credits);
        line2 = findViewById(R.id.creator_credits);

        header.setTypeface(Const.appFont);
        line1.setTypeface(Const.appFont);
        line2.setTypeface(Const.appFont);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back_from_credits) {
            Intent i = new Intent(CreditsActivity.this, MainActivity.class);
            Bundle b = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            startActivity(i, b);
        }
    }
}