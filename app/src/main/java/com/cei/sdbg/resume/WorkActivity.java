package com.cei.sdbg.resume;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class WorkActivity extends AppCompatActivity {

    private DataManager mDataManager;
    private ArrayAdapter<String> mFuturePlanAdapter;
    private ArrayAdapter<String> mJobTypeAdapter;
    private ArrayAdapter<String> mJobFunctionAdapter;

    private Spinner mFutureSpinner;
    private Spinner mJobFunctionSpinner;
    private EditText mFutureOther;
    private EditText mJobFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = DataManager.getmInstance();

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        setContentView(R.layout.activity_work);
        mFutureSpinner = (Spinner) findViewById(R.id.futureplan_spinner);
        mJobFunctionSpinner = (Spinner) findViewById(R.id.jobfunction_spinner);
        mFutureOther = (EditText)findViewById(R.id.edit_other_feature);
        mJobFunction = (EditText)findViewById(R.id.edit_other_interview);
        setupFuturePlans();
        setupJobTypes();
        setupJobFunctions();
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

    void loadData() {
        int futurePlanPosition = 0;
        int jobTypePosition = 0;
        int jobFunctionPosition = 0;
        String other = getString(R.string.other);

        if (mFuturePlanAdapter != null) {
            futurePlanPosition =
                    mFuturePlanAdapter.getPosition(mDataManager.getFuturePlan());

            if (futurePlanPosition < 0) {
                if (mDataManager.getFuturePlan().length() > 0) {
                    futurePlanPosition = mFuturePlanAdapter.getPosition(other);
                    mFutureOther.setVisibility(View.VISIBLE);
                    mFutureOther.setSingleLine();
                    mFutureOther.setMaxLines(1);
                    mFutureOther.setText(mDataManager.getFuturePlan());
                } else {
                    futurePlanPosition = 0;
                    mFutureOther.setVisibility(View.INVISIBLE);
                }
            }
        }
        mFutureSpinner.setSelection(futurePlanPosition);

        if (mJobTypeAdapter != null) {
            jobTypePosition =
                    mJobTypeAdapter.getPosition(mDataManager.getJobType());

            if (jobTypePosition < 0) {
                jobTypePosition = 0;
            }
        }
        ((Spinner)findViewById(R.id.jobtype_spinner)).setSelection(jobTypePosition);

        if (mJobFunctionAdapter != null) {
            jobFunctionPosition =
                    mJobFunctionAdapter.getPosition(mDataManager.getJobFunction());

            if (jobFunctionPosition < 0) {
                if (mDataManager.getJobFunction().length() > 0) {
                    jobFunctionPosition = mJobFunctionAdapter.getPosition(other);
                    mJobFunction.setVisibility(View.VISIBLE);
                    mJobFunction.setSingleLine();
                    mJobFunction.setMaxLines(1);
                    mJobFunction.setText(mDataManager.getJobFunction());
                } else {
                    jobFunctionPosition = 0;
                    mJobFunction.setVisibility(View.INVISIBLE);
                }
            }
        }
        mJobFunctionSpinner.setSelection(jobFunctionPosition);

        ((EditText)findViewById(R.id.available_edittext)).setText(mDataManager.getAvailableDate());
    }

    void setupFuturePlans() {
        String[] futurePlans = getResources().getStringArray(R.array.array_future_plan);
        Log.d("AA", "futurePlans" + futurePlans[1]);
        Log.d("AA", "futurePlans" + futurePlans[2]);
        //Spinner spinner = (Spinner)findViewById(R.id.futureplan_spinner);
        mFuturePlanAdapter = new ArrayAdapter<String>(this, R.layout.education_spinner_item, futurePlans);

        mFuturePlanAdapter.setDropDownViewResource(R.layout.education_spinner_item);
        mFutureSpinner.setAdapter(mFuturePlanAdapter);
        mFutureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mFuturePlanAdapter.getItem(position).equals(getString(R.string.other))) {
                    mFutureOther.setVisibility(View.VISIBLE);
                    mFutureOther.setSingleLine();
                    mFutureOther.setMaxLines(1);
                } else {
                    mFutureOther.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void setupJobTypes() {
        String[] jobTypes = getResources().getStringArray(R.array.array_job_type);
        Spinner spinner = (Spinner)findViewById(R.id.jobtype_spinner);
        mJobTypeAdapter = new ArrayAdapter<String>(this, R.layout.education_spinner_item, jobTypes);

        mJobTypeAdapter.setDropDownViewResource(R.layout.education_spinner_item);
        spinner.setAdapter(mJobTypeAdapter);
    }

    void setupJobFunctions() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> jobFunctions = dbHelper.getAllJobFunctions();
        //Spinner spinner = (Spinner)findViewById(R.id.jobfunction_spinner);
        mJobFunctionAdapter = new ArrayAdapter<String>(this, R.layout.education_spinner_item, jobFunctions);

        mJobFunctionAdapter.setDropDownViewResource(R.layout.education_spinner_item);
        mJobFunctionSpinner.setAdapter(mJobFunctionAdapter);
        mJobFunctionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mJobFunctionAdapter.getItem(position).equals(getString(R.string.other))) {
                    mJobFunction.setVisibility(View.VISIBLE);
                    mJobFunction.setSingleLine();
                    mJobFunction.setMaxLines(1);
                } else {
                    mJobFunction.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    DatePickerDialog.OnDateSetListener mAvailableDatePicker
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = String.format("%4d/%02d/%02d", year, monthOfYear+1, dayOfMonth);

            ((EditText)findViewById(R.id.available_edittext)).setText(date);
        }
    };

    void onAvailableClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mAvailableDatePicker,
                        year, monthOfYear, dayOfMonth);

        datePickerDialog.show();
    }

    void storeData() {
        if (mFutureOther.getVisibility() == View.INVISIBLE) {
            mDataManager.setFuturePlan(((Spinner) findViewById(R.id.futureplan_spinner)).getSelectedItem().toString());
        } else {
            mDataManager.setFuturePlan(mFutureOther.getText().toString());
        }
        mDataManager.setJobtype(((Spinner)findViewById(R.id.jobtype_spinner)).getSelectedItem().toString());
        if (mJobFunction.getVisibility() == View.INVISIBLE) {
            mDataManager.setJobFunction(((Spinner) findViewById(R.id.jobfunction_spinner)).getSelectedItem().toString());
        } else {
            mDataManager.setJobFunction(mJobFunction.getText().toString());
        }
        mDataManager.setAvailableDate(((EditText)findViewById(R.id.available_edittext)).getText().toString());
    }

    boolean isDataValid() {
        boolean isDataValid = true;
        int colorRed = getResources().getColor(R.color.colorRed);
        int textColorNormal = getResources().getColor(R.color.textColor);
        String futurePlan = ((Spinner)findViewById(R.id.futureplan_spinner)).getSelectedItem().toString();
        String jobType = ((Spinner)findViewById(R.id.jobtype_spinner)).getSelectedItem().toString();
        String jobFunction = ((Spinner)findViewById(R.id.jobfunction_spinner)).getSelectedItem().toString();
        String availableDate = ((EditText)findViewById(R.id.available_edittext)).getText().toString();
        String chooseText = getString(R.string.please_choose);

        if (TextUtils.isEmpty(futurePlan) || chooseText.equals(futurePlan)) {
            ((TextView)findViewById(R.id.futureplan_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else if (futurePlan.equals(getString(R.string.other))
                && mFutureOther.getText().toString().trim().length() < 1){
            ((TextView)findViewById(R.id.futureplan_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.futureplan_textview)).setTextColor(textColorNormal);
        }

        if (TextUtils.isEmpty(jobType) || chooseText.equals(jobType)) {
            ((TextView)findViewById(R.id.jobtype_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.jobtype_textview)).setTextColor(textColorNormal);
        }

        if (TextUtils.isEmpty(availableDate) || chooseText.equals(availableDate)) {
            ((TextView)findViewById(R.id.available_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.available_textview)).setTextColor(textColorNormal);
        }

        if (jobFunction.equals(getString(R.string.other))
                && mJobFunction.getText().toString().trim().length() < 1) {
            ((TextView)findViewById(R.id.jobfunction_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.jobfunction_textview)).setTextColor(textColorNormal);
        }

        return isDataValid;
    }

    void onNextButtonClicked(View view) {
        TextView warningMessage = (TextView)findViewById(R.id.check_data_textview);

        if (isDataValid()) {
            Intent intent = new Intent(this, ReviewActivity.class);

            overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
            startActivity(intent);
            storeData();
            finish();
        } else {
            warningMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //Disable Back key for this activity
    }
}
