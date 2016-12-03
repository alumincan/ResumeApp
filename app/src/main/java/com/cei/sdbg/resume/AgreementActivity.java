package com.cei.sdbg.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        setContentView(R.layout.activity_agreement);
    }

    void onCheckBoxClicked(View view) {
        if (view instanceof CheckBox) {
            Button enterButton = (Button)findViewById(R.id.enter_button);
            CheckBox agreeCheckbox = (CheckBox)view;
            if (agreeCheckbox.isChecked()) {
                enterButton.setEnabled(true);
            } else {
                enterButton.setEnabled(false);
            }
        }

    }

    void onExitButtonClicked(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);

        intent.putExtra("activity", "AgreementActivity");
        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
        startActivity(intent);
        finish();
    }

    void onEnterButtonClicked(View view) {
        Intent intent = new Intent(this, BasicInfoActivity.class);

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //Disable Back key for this activity
    }
}
