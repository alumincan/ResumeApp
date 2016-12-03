package com.cei.sdbg.resume;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Calendar;


public class BasicInfoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    int[] region_array_list = {
            R.array.array_taipei_region,   //台北
            R.array.array_chilong_city_region,  //基隆
            R.array.array_lainjaing_region,     //連江
            R.array.array_new_taipei_region,    //新北
            R.array.arrat_yilan_region,
            R.array.array_shinchu_city_region,
            R.array.array_shinchu_region,
            R.array.array_taoyaun_city_region,
            R.array.array_maioli_reigon,
            R.array.array_taichung_city_region,
            R.array.array_changhua_region,
            R.array.array_nantau_reigon,
            R.array.array_chiayi_city_region,
            R.array.array_chiayi_region,
            R.array.array_ulin_region,
            R.array.array_tainan_city_region,
            R.array.array_kaoshuon_city_region,
            R.array.array_south_sea_region,
            R.array.array_pengho_region,
            R.array.array_kingman_region,
            R.array.array_pington_region,
            R.array.array_taitong_region,
            R.array.array_hualen_region
    };

    static final int DEFAULT_AGE = 22;
    static final int DEFAULT_BIRTH_MONTH = Calendar.JANUARY;
    static final int DEFAULT_BIRTH_DAY = 1;
    private DataManager mDataManager;
    private ArrayAdapter<String> mCityArrayAdapter;
    private ArrayAdapter<String> mRegionArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = DataManager.getmInstance();

        if (!TextUtils.isEmpty(getIntent().getStringExtra("activity"))) {
            overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
        } else {
            overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        }
        setContentView(R.layout.activity_basic_info);

        mDataManager.setFillingTime();
        setupAddress();
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
        final RadioButton maleButton = (RadioButton)findViewById(R.id.male_radiobutton);
        final RadioButton femaleButton = (RadioButton)findViewById(R.id.female_radiobutton);
        final EditText birthdayEditText = (EditText)findViewById(R.id.birthday_edittext);
        final RadioButton militaryNoneButton = (RadioButton)findViewById(R.id.military_none_radiobutton);
        final RadioButton militaryWaivedButton = (RadioButton)findViewById(R.id.military_waived_radiobutton);
        final RadioButton militaryCompletedButton = (RadioButton)findViewById(R.id.military_completed_radiobutton);
        final RadioButton militaryCurrentButton = (RadioButton)findViewById(R.id.military_current_radiobutton);
        final Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
        final Spinner regionSpinner = (Spinner)findViewById(R.id.region_spinner);

        maleButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(maleButton);
                return false;
            }
        });
        femaleButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(femaleButton);
                return false;
            }
        });
        birthdayEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(birthdayEditText);
                return false;
            }
        });
        militaryNoneButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(militaryNoneButton);
                return false;
            }
        });
        militaryWaivedButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(militaryWaivedButton);
                return false;
            }
        });
        militaryCompletedButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(militaryCompletedButton);
                return false;
            }
        });
        militaryCurrentButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(militaryCurrentButton);
                return false;
            }
        });
        citySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(citySpinner);
                return false;
            }
        });
        regionSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeypad(regionSpinner);
                return false;
            }
        });
    }

    private void loadData() {
        int cityPosition = 0;
        int regionPosition = 0;

        ((EditText)findViewById(R.id.name_edittext)).setText(mDataManager.getName());
        ((EditText)findViewById(R.id.id_number_edittext)).setText(mDataManager.getIdNumber());

        if (getString(R.string.female).equals(mDataManager.getGender())) {
            ((RadioButton)findViewById(R.id.female_radiobutton)).setChecked(true);
        } else {
            ((RadioButton)findViewById(R.id.male_radiobutton)).setChecked(true);
        }

        ((EditText)findViewById(R.id.birthday_edittext)).setText(mDataManager.getBirthday());

        if (getString(R.string.military_waived).equals(mDataManager.getMilitaryStatus())) {
            ((RadioButton)findViewById(R.id.military_waived_radiobutton)).setChecked(true);
        } else if (getString(R.string.military_completed).equals(mDataManager.getMilitaryStatus())) {
            ((RadioButton)findViewById(R.id.military_completed_radiobutton)).setChecked(true);
        } else if (getString(R.string.military_current).equals(mDataManager.getMilitaryStatus())) {
            ((RadioButton)findViewById(R.id.military_current_radiobutton)).setChecked(true);
        } else {
            ((RadioButton)findViewById(R.id.military_none_radiobutton)).setChecked(true);
        }

        ((EditText)findViewById(R.id.phone_edittext)).setText(mDataManager.getPhoneNumber());
        ((EditText)findViewById(R.id.email_edittext)).setText(mDataManager.getEmail());

        if (mCityArrayAdapter != null) {
            cityPosition = mCityArrayAdapter.getPosition(mDataManager.getCityAddress());

            if (cityPosition < 0) {
                cityPosition = 0;
            }
        }

        if (mRegionArrayAdapter != null) {
            regionPosition = mCityArrayAdapter.getPosition(mDataManager.getRegionAddress());

            if (regionPosition < 0) {
                regionPosition = 0;
            }
        }

        ((Spinner)findViewById(R.id.city_spinner)).setSelection(cityPosition);
        ((Spinner)findViewById(R.id.region_spinner)).setSelection(regionPosition);
    }

    private void setupAddress() {
        Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
        String[] cityList = getResources().getStringArray(R.array.array_taiwan_cities);
        mCityArrayAdapter = new ArrayAdapter<String>(this, R.layout.address_spinner_item, cityList);

        mCityArrayAdapter.setDropDownViewResource(R.layout.address_spinner_item);
        citySpinner.setAdapter(mCityArrayAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupRegion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citySpinner.setSelection(0);

    }

    void setupRegion(int position) {
        Spinner regionSpinner = (Spinner)findViewById(R.id.region_spinner);
        String[] regionList = getResources().getStringArray(region_array_list[position]);
        mRegionArrayAdapter = new ArrayAdapter<String>(this, R.layout.address_spinner_item, regionList);

        mRegionArrayAdapter.setDropDownViewResource(R.layout.address_spinner_item);
        regionSpinner.setAdapter(mRegionArrayAdapter);
        regionSpinner.setSelection(0);
        regionSpinner.setDropDownVerticalOffset(0);

    }

    void storeData() {
        int selectedGenderId = ((RadioGroup)findViewById(R.id.gender_radiogroup)).getCheckedRadioButtonId();
        int selectedMilitaryStatusId = ((RadioGroup)findViewById(R.id.military_radiogroup)).getCheckedRadioButtonId();

        mDataManager.setName(((EditText)findViewById(R.id.name_edittext)).getText().toString());
        mDataManager.setIdNumber(((EditText)findViewById(R.id.id_number_edittext)).getText().toString());
        mDataManager.setGender(((RadioButton)findViewById(selectedGenderId)).getText().toString());
        mDataManager.setmBirthday(((EditText)findViewById(R.id.birthday_edittext)).getText().toString());
        mDataManager.setMilitaryStatus(((RadioButton)findViewById(selectedMilitaryStatusId)).getText().toString());
        mDataManager.setPhoneNumber(((EditText)findViewById(R.id.phone_edittext)).getText().toString());
        mDataManager.setEmail(((EditText)findViewById(R.id.email_edittext)).getText().toString());
        mDataManager.setCityAddress(((Spinner)findViewById(R.id.city_spinner)).getSelectedItem().toString());
        mDataManager.setRegionAddress(((Spinner)findViewById(R.id.region_spinner)).getSelectedItem().toString());
    }

    private boolean isDataValid(){
        boolean isDataValid = true;
        EditText nameEditText = (EditText)findViewById(R.id.name_edittext);
        EditText idNumberEditText = (EditText)findViewById(R.id.id_number_edittext);
        EditText phoneNumberEditText = (EditText)findViewById(R.id.phone_edittext);
        EditText emailEditText = (EditText)findViewById(R.id.email_edittext);
        int colorRed = getResources().getColor(R.color.colorRed);
        int textColorNormal = getResources().getColor(R.color.textColor);


        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            ((TextView)findViewById(R.id.name_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.name_textview)).setTextColor(textColorNormal);
        }

        if (TextUtils.isEmpty(idNumberEditText.getText().toString())) {
            ((TextView)findViewById(R.id.id_number_textview)).setTextColor(colorRed);

            isDataValid = false;
        }  else {
            ((TextView)findViewById(R.id.id_number_textview)).setTextColor(textColorNormal);
        }

        if (TextUtils.isEmpty(phoneNumberEditText.getText().toString())) {
            ((TextView)findViewById(R.id.phonenumber_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.phonenumber_textview)).setTextColor(textColorNormal);
        }

        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            ((TextView)findViewById(R.id.email_textview)).setTextColor(colorRed);

            isDataValid = false;
        } else {
            ((TextView)findViewById(R.id.email_textview)).setTextColor(textColorNormal);
        }


        return isDataValid;
    }

    void onNextButtonClicked(View view) {
        Intent intent = new Intent(this, EducationActivity.class);
        TextView warningMessage = (TextView)findViewById(R.id.check_data_textview);

        if (isDataValid()) {
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

    protected void onBirthdayClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int defaultBirthYear = calendar.get(Calendar.YEAR) - DEFAULT_AGE;
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        this, defaultBirthYear, DEFAULT_BIRTH_MONTH, DEFAULT_BIRTH_DAY);

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String birthday = String.format("%4d/%02d/%02d", year, monthOfYear+1, dayOfMonth);

        ((EditText)findViewById(R.id.birthday_edittext)).setText(birthday);
    }
}
