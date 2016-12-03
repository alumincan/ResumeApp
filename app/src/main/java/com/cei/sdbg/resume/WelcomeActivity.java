package com.cei.sdbg.resume;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    AlertDialog mPasswordDialog;
    AlertDialog mSettingsDialog;
    AlertDialog mEditEventDialog;
    //AlertDialog mEditJobFunctionDialog;
    JobDialog mEditJobFunctionDialog;

    private final String DEFAULT_PASSWORD = "0385";
    static final String PREF_FILE = "sdbgresume";
    private final String EVENT_KEY = "event";
    private String mEvent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (TextUtils.isEmpty(getIntent().getStringExtra("activity"))) {
            overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        } else {
            overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
        }
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView eventTextView = (TextView)findViewById(R.id.event_textview);
        SharedPreferences sharedPref =
                getApplicationContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        mEvent = sharedPref.getString(EVENT_KEY, getString(R.string.ntu));
        eventTextView.setText(getString(R.string.event_format, mEvent));
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LayoutInflater inflater = getLayoutInflater();
            View signinLayout = inflater.inflate(R.layout.dialog_signin, null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            alertBuilder.setView(signinLayout);
            alertBuilder.setCancelable(false);
            mPasswordDialog = alertBuilder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    void onSettingsButtonClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View signinLayout = inflater.inflate(R.layout.dialog_signin, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setView(signinLayout);
        alertBuilder.setCancelable(false);
        mPasswordDialog = alertBuilder.show();
    }

    void onStartButtonClicked(View view) {
        DataManager dataManager = DataManager.getmInstance();
        Intent intent = new Intent(this, AgreementActivity.class);

        overridePendingTransition(R.anim.slide_next_in, R.anim.slide_next_out);
        dataManager.setEvent(mEvent);
        startActivity(intent);
        finish();
    }

    void onExitButtonClicked(View view) {
        mPasswordDialog.dismiss();
    }

    void onLoginButtonClicked(View view) {
        EditText passwordEditText = (EditText)mPasswordDialog.findViewById(R.id.password_edittext);
        String password = passwordEditText.getText().toString();

        if (!DEFAULT_PASSWORD.equals(password)) {
            ((TextView)mPasswordDialog.findViewById(R.id.wrong_password_textview)).setVisibility(View.VISIBLE);
            passwordEditText.setText("");
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View settingsView = inflater.inflate(R.layout.dialog_advance_settings, null);

            alertBuilder.setView(settingsView);
            alertBuilder.setCancelable(false);
            mPasswordDialog.dismiss();
            mSettingsDialog = alertBuilder.show();
        }
    }

    void onExitSettingsButtonClicked(View view) {
        mSettingsDialog.dismiss();
    }

    void populateEvents() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> allEvents = dbHelper.getAllEvents();
        ArrayAdapter<String> eventAdapter =
                new ArrayAdapter<String>(this, R.layout.event_list_item, allEvents);
        ListView eventListView = (ListView)mEditEventDialog.findViewById(R.id.events_listview);

        eventListView.setAdapter(eventAdapter);
    }

    void onEditEventButtonClicked(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View editEventView = inflater.inflate(R.layout.dialog_edit_events, null);

        alertBuilder.setView(editEventView);
        alertBuilder.setCancelable(false);
        mSettingsDialog.dismiss();
        mEditEventDialog = alertBuilder.show();
        populateEvents();
    }

    void populateJobFunctions() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> allJobFunctions = dbHelper.getAllJobFunctions();
        ArrayAdapter<String> jobFunctionAdapter =
                new ArrayAdapter<String>(this, R.layout.event_list_item, allJobFunctions);
        ListView jobFunctionListView = (ListView)mEditJobFunctionDialog.findViewById(R.id.jobfunction_listview);

        jobFunctionListView.setAdapter(jobFunctionAdapter);
    }

    void onEditJobFunctionButtonClicked(View view) {
        /*AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View editJobFunctionView = inflater.inflate(R.layout.dialog_edit_job_functions, null);

        alertBuilder.setView(editJobFunctionView);
        alertBuilder.setCancelable(false);
        mSettingsDialog.dismiss();
        mEditJobFunctionDialog = alertBuilder.show();
        populateJobFunctions();*/
        mEditJobFunctionDialog = new JobDialog(this);
        mEditJobFunctionDialog.show();
    }

    void onUploadButtonClicked(View view ) {

    }

    void onExitEventClicked(View view) {
        mEditEventDialog.dismiss();
        mSettingsDialog.show();
    }

    void onAddEventClicked(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View addEventView = inflater.inflate(R.layout.dialog_add_item, null);

        alertBuilder.setView(addEventView);
        alertBuilder.setCancelable(false);
        final AlertDialog alertDialog = alertBuilder.show();

        ((TextView)addEventView.findViewById(R.id.title)).setText(getString(R.string.add_event));
        ((Button)addEventView.findViewById(R.id.canel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        ((Button)addEventView.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                String newEvent = ((EditText)addEventView.findViewById(R.id.edittext)).getText().toString();
                List<String> mEvents = dbHelper.getAllEvents();

                if (dbHelper.isEventExisted(newEvent)) {
                    TextView errorMessageTextView = (TextView)addEventView.findViewById(R.id.error_message);

                    errorMessageTextView.setText(getString(R.string.duplicate_event));
                    errorMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    dbHelper.removeEvent(mEvents.get(mEvents.size() -1));
                    dbHelper.addEvent(newEvent);
                    populateEvents();
                    alertDialog.dismiss();
                }
            }
        });

    }

    void onDeleteEventClicked(View view) {
        ListView eventListView = (ListView) mEditEventDialog.findViewById(R.id.events_listview);
        int selectedPosition = eventListView.getCheckedItemPosition();

        if (selectedPosition != -1) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View confirmationView = inflater.inflate(R.layout.dialog_confirm, null);

            alertBuilder.setView(confirmationView);
            alertBuilder.setCancelable(false);
            final AlertDialog alertDialog = alertBuilder.show();
            final String selectedEvent = eventListView.getAdapter().getItem(selectedPosition).toString();
            String confirmMessage = getString(R.string.delete_event_message, selectedEvent);

            ((TextView)confirmationView.findViewById(R.id.title)).setText(getString(R.string.delete_event));
            ((TextView)confirmationView.findViewById(R.id.message)).setText(confirmMessage);
            ((Button)confirmationView.findViewById(R.id.canel_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            ((Button)confirmationView.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

                    dbHelper.removeEvent(selectedEvent);
                    populateEvents();
                    alertDialog.dismiss();
                }
            });
        }
    }

    TextView getEventTextView() {
        return (TextView)findViewById(R.id.event_textview);
    }
    void onSelectEventClicked(View view) {
        ListView eventListView = (ListView)mEditEventDialog.findViewById(R.id.events_listview);
        int selectedEventPosition = eventListView.getCheckedItemPosition();

        if (selectedEventPosition != -1) {
            mEvent = eventListView.getAdapter().getItem(selectedEventPosition).toString();
            if (mEvent.equals(getResources().getString(R.string.cust_event_school))) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_add_new_event), Toast.LENGTH_LONG).show();
                return;
            }
            SharedPreferences sharedPref =
                    getApplicationContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            getEventTextView().setText(getString(R.string.event_format, mEvent));
            editor.putString(EVENT_KEY, mEvent);
            editor.commit();

            mSettingsDialog.show();
            mEditEventDialog.dismiss();
        }
    }

    void onExitJobFunctionClicked(View view) {
        mEditJobFunctionDialog.dismiss();
        mSettingsDialog.show();
    }

    void onAddJobFunctionClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final View addJobFunctionView = inflater.inflate(R.layout.dialog_add_item, null);
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setView(addJobFunctionView);
        alertBuilder.setCancelable(false);
        final AlertDialog alertDialog = alertBuilder.show();

        ((TextView)addJobFunctionView.findViewById(R.id.title)).setText(getString(R.string.add_job_function));
        ((Button)addJobFunctionView.findViewById(R.id.canel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        ((Button)addJobFunctionView.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                String newJobFunction =
                        ((EditText)addJobFunctionView.findViewById(R.id.edittext)).getText().toString();

                if (dbHelper.isJobFunctionExisted(newJobFunction)) {
                    TextView warningMessageTextView = (TextView)addJobFunctionView.findViewById(R.id.error_message);

                    warningMessageTextView.setText(getString(R.string.duplicate_job_function));
                    warningMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    dbHelper.addJobFunction(newJobFunction);
                    populateJobFunctions();
                    alertDialog.dismiss();
                }
            }
        });

    }

    void onDeleteJobFunctionClicked(View view) {
        ListView jobFunctionListView =
                (ListView)mEditJobFunctionDialog.findViewById(R.id.jobfunction_listview);
        int selectedJobFunctionPosition = jobFunctionListView.getCheckedItemPosition();
        Log.d("AAAA", "selectedJobFunctionPosition = " + selectedJobFunctionPosition);
        Log.d("AAAA", "jobFunctionListView counts = " + jobFunctionListView.getCount());
        if (selectedJobFunctionPosition != -1) {
            LayoutInflater inflater = getLayoutInflater();
            View deleteConfirmView = inflater.inflate(R.layout.dialog_confirm, null);
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            final String selectedJobFunction =
                    jobFunctionListView.getAdapter().getItem(selectedJobFunctionPosition).toString();
            String deleteJobFunctionMessage = getString(R.string.delete_event_message, selectedJobFunction);

            alertBuilder.setView(deleteConfirmView);
            alertBuilder.setCancelable(false);
            final AlertDialog alertDialog = alertBuilder.show();

            ((TextView)deleteConfirmView.findViewById(R.id.title)).setText(getString(R.string.delete_event));
            ((TextView)deleteConfirmView.findViewById(R.id.message)).setText(deleteJobFunctionMessage);
            ((Button)deleteConfirmView.findViewById(R.id.canel_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            ((Button)deleteConfirmView.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

                    dbHelper.removeJobFunction(selectedJobFunction);
                    populateJobFunctions();
                    alertDialog.dismiss();
                }
            });
        }
    }
}