package com.cei.sdbg.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {
    DataManager mDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        setContentView(R.layout.activity_review);

        mDataManager = DataManager.getmInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    void loadData() {
        String address = mDataManager.getCityAddress() + mDataManager.getRegionAddress();
        String schoolPeriod =
                mDataManager.getSchoolStart() + getString(R.string.between) + mDataManager.getSchoolEnd();


        ((TextView)findViewById(R.id.name_textview)).setText(mDataManager.getName());
        ((TextView)findViewById(R.id.idnumber_textview)).setText(mDataManager.getIdNumber());
        ((TextView)findViewById(R.id.gender_textview)).setText(mDataManager.getGender());
        ((TextView)findViewById(R.id.birthday_textview)).setText(mDataManager.getBirthday());
        ((TextView)findViewById(R.id.military_textview)).setText(mDataManager.getMilitaryStatus());
        ((TextView)findViewById(R.id.phone_textview)).setText(mDataManager.getPhoneNumber());
        ((TextView)findViewById(R.id.email_textview)).setText(mDataManager.getEmail());
        ((TextView)findViewById(R.id.address_textview)).setText(address);
        ((TextView)findViewById(R.id.highesteducation_textview)).setText(mDataManager.getHighestEducation());
        ((TextView)findViewById(R.id.school_textview)).setText(mDataManager.getSchool());
        ((TextView)findViewById(R.id.major_textview)).setText(mDataManager.getMajor());
        ((TextView)findViewById(R.id.schoolperiod_textview)).setText(schoolPeriod);
        ((TextView)findViewById(R.id.graduatethisyear_textview)).setText(mDataManager.isGraduateThisYear());
        ((TextView)findViewById(R.id.skill_textview)).setText(mDataManager.getSkill());
        ((TextView)findViewById(R.id.futureplan_textview)).setText(mDataManager.getFuturePlan());
        ((TextView)findViewById(R.id.jobtype_textview)).setText(mDataManager.getJobType());
        ((TextView)findViewById(R.id.jobfunction_textview)).setText(mDataManager.getJobFunction());
        ((TextView)findViewById(R.id.availabledate_textview)).setText(mDataManager.getAvailableDate());
    }

    void onModifyButtonClicked(View view) {
        Intent intent = new Intent(this, BasicInfoActivity.class);

        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
        intent.putExtra("activity", "ReviewActivity");
        startActivity(intent);
        finish();
    }

    void onSubmitButtonClicked(View view) {
        Intent intent = new Intent(this, CompleteActivity.class);

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        startActivity(intent);
        mDataManager.saveToFile(this);
        mDataManager.resetDataManager();
        finish();
    }

    @Override
    public void onBackPressed() {
        //Disable Back key for this activity
    }
}
