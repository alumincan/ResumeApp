package com.cei.sdbg.resume;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CompleteActivity extends AppCompatActivity {
    static final int DELAY_INTERVAL = 30000;       //30 seconds
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        setContentView(R.layout.activity_complete);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            backToWelcomePage();
            }
        }, DELAY_INTERVAL);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isVisible = true;
    }

    @Override
    protected void onPause() {
        isVisible = false;

        super.onPause();
    }

    void backToWelcomePage() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);

        //Only launch WelcomeActivity when the activity is in foreground
        if (isVisible) {
            overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
            startActivity(intent);
        }

        finish();
    }

    void onBackWelcomeClicked (View view) {
        backToWelcomePage();
    }

    @Override
    public void onBackPressed() {
        //Disable Back key for this activity
    }
}
