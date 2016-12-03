package com.cei.sdbg.resume;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class EducationActivity extends AppCompatActivity {
    static final int DEFAULT_START_MONTH = Calendar.SEPTEMBER;
    static final int DEFAULT_START_DAY = 1;
    static final int DEFAULT_END_MONTH = Calendar.JUNE;
    static final int DEFAULT_END_DAY = 22;
    static final int DEFAULT_DURATION = 4;
    private DataManager mDataManager;
    private ArrayAdapter<String> mHighestEducationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = DataManager.getmInstance();

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        setContentView(R.layout.activity_education);

        setupHighestEducation();
        hideKeypadForSomeControls();
    }
    @Override
    protected void onPause() {
        storeData();
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    void hideKeypad(final View view) {
        final InputMethodManager inputManager =
                (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }, 100);
    }

    void hideKeypadForSomeControls() {
        final Spinner highestEducation = (Spinner)findViewById(R.id.highesteducation_spinner);
        final EditText schoolStartEditText = (EditText)findViewById(R.id.schoolstart_edittext);
        final EditText schoolEndEditText = (EditText)findViewById(R.id.schoolend_edittext);
        final RadioButton yesButton = (RadioButton)findViewById(R.id.yes_radiobutton);
        final RadioButton noButton = (RadioButton)findViewById(R.id.no_radiobutton);

        highestEducation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(highestEducation);
                return false;
            }
        });
        schoolStartEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(schoolStartEditText);
                return false;
            }
        });
        schoolEndEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(schoolEndEditText);
                return false;
            }
        });
        yesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(yesButton);
                return false;
            }
        });
        noButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(noButton);
                return false;
            }
        });
    }

    private void loadData() {
        int highestEducationPosition = 0;

        if (mHighestEducationAdapter != null) {
            highestEducationPosition =
                    mHighestEducationAdapter.getPosition(mDataManager.getHighestEducation());

            if (highestEducationPosition < 0) {
                highestEducationPosition = 0;
            }
        }

        ((Spinner)findViewById(R.id.highesteducation_spinner)).setSelection(highestEducationPosition);

        ((EditText)findViewById(R.id.school_edittext)).setText(mDataManager.getSchool());
        ((EditText)findViewById(R.id.major_edittext)).setText(mDataManager.getMajor());
        ((EditText)findViewById(R.id.schoolstart_edittext)).setText(mDataManager.getSchoolStart());
        ((EditText)findViewById(R.id.schoolend_edittext)).setText(mDataManager.getSchoolEnd());

        if (getString(R.string.no).equals(mDataManager.isGraduateThisYear())) {
            ((RadioButton)findViewById(R.id.no_radiobutton)).setChecked(true);
        } else {
            ((RadioButton)findViewById(R.id.yes_radiobutton)).setChecked(true);
        }

        ((EditText)findViewById(R.id.skill_edittext)).setText(mDataManager.getSkill());
    }

    private void setupHighestEducation() {
        String[] degrees = getResources().getStringArray(R.array.array_degree);
        Spinner spinner = (Spinner)findViewById(R.id.highesteducation_spinner);
        mHighestEducationAdapter = new ArrayAdapter<String>(this, R.layout.education_spinner_item, degrees);

        mHighestEducationAdapter.setDropDownViewResource(R.layout.education_spinner_item);
        spinner.setAdapter(mHighestEducationAdapter);
    }

    void onNextButtonClicked(View view) {
        TextView warningMessage = (TextView)findViewById(R.id.check_data_textview);

        if (isDataValid()) {
            Intent intent = new Intent(this, WorkActivity.class);

            overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
            startActivity(intent);
            storeData();
            finish();
        } else {
            warningMessage.setVisibility(View.VISIBLE);
        }
    }

    boolean isDataValid() {
        EditText schoolEditText = (EditText)findViewById(R.id.school_edittext);
        EditText majorEditText = (EditText)findViewById(R.id.major_edittext);
        EditText schoolStartEditText = (EditText)findViewById(R.id.schoolstart_edittext);
        EditText schoolEndEditText = (EditText)findViewById(R.id.schoolend_edittext);
        EditText skillEditText = (EditText)findViewById(R.id.skill_edittext);
        boolean isDataValid = true;
        int colorRed = getResources().getColor(R.color.colorRed);
        int textColorNormal = getResources().getColor(R.color.textColor);

        if (TextUtils.isEmpty(schoolEditText.getText().toString())) {
            ((TextView)findViewById(R.id.school_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.school_textview)).setTextColor(textColorNormal);
        }

        if (TextUtils.isEmpty(majorEditText.getText().toString())) {
            ((TextView)findViewById(R.id.major_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.major_textview)).setTextColor(textColorNormal);

        }

        if (TextUtils.isEmpty(schoolStartEditText.getText().toString()) ||
                TextUtils.isEmpty(schoolEndEditText.getText().toString())) {
            ((TextView)findViewById(R.id.schoolperiod_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.schoolperiod_textview)).setTextColor(textColorNormal);

        }

        if (TextUtils.isEmpty(skillEditText.getText().toString())) {
            ((TextView)findViewById(R.id.skill_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.skill_textview)).setTextColor(textColorNormal);

        }

        return isDataValid;
    }

    void storeData() {
        boolean isGraduateThisYear = true;
        int graduateThisYearSelectedButtonId =
                ((RadioGroup)findViewById(R.id.graduatethisyear_radiogroup)).getCheckedRadioButtonId();
        String graduateThisYearString =
                ((RadioButton)findViewById(graduateThisYearSelectedButtonId)).getText().toString();

        mDataManager.setHighestEducation(
                ((Spinner)findViewById(R.id.highesteducation_spinner)).getSelectedItem().toString());
        mDataManager.setSchool(((EditText)findViewById(R.id.school_edittext)).getText().toString());
        mDataManager.setMajor(((EditText)findViewById(R.id.major_edittext)).getText().toString());
        mDataManager.setSchoolStart(((EditText)findViewById(R.id.schoolstart_edittext)).getText().toString());
        mDataManager.setSchoolEnd(((EditText)findViewById(R.id.schoolend_edittext)).getText().toString());
        mDataManager.setIsGraduateThisYear(graduateThisYearString);
        mDataManager.setSkill(((EditText)findViewById(R.id.skill_edittext)).getText().toString());
    }

    DatePickerDialog.OnDateSetListener mSchoolStartDatePicker
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = String.format("%4d/%02d/%02d", year, monthOfYear+1, dayOfMonth);

            ((EditText)findViewById(R.id.schoolstart_edittext)).setText(date);
        }
    };

    void onSchoolStartButtonClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int defaultStartYear = calendar.get(Calendar.YEAR) - DEFAULT_DURATION;
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mSchoolStartDatePicker,
                        defaultStartYear, DEFAULT_START_MONTH, DEFAULT_START_DAY);
        datePickerDialog.getDatePicker().setCalendarViewShown(true);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener mSchoolEndDatePicker
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = String.format("%4d/%02d/%02d", year, monthOfYear+1, dayOfMonth);

            ((EditText)findViewById(R.id.schoolend_edittext)).setText(date);
        }
    };

    void onSchoolEndButtonClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int defaultStartYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mSchoolEndDatePicker,
                        defaultStartYear, DEFAULT_END_MONTH, DEFAULT_END_DAY);
        //datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        //Disable Back key for this activity
    }
}
