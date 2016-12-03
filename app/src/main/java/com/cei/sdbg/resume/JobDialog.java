package com.cei.sdbg.resume;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alumincan on 2016/11/15.
 */
public class JobDialog extends Dialog{
    private Boolean DEBUG = true;
    private Context mContext;
    private ListView mListView;
    private Button mOKButton;
    private Button mDeleteButton;
    private Button mEditButton;
    private ArrayAdapter<String> jobFunctionAdapter;
    private JobArrayAdapter mAdapter;
    private DatabaseHelper dbHelper;
    private boolean inDeleteMode = false;
    private final String TAG = "JobDialog";

    public JobDialog(Context context) {
        super(context);
        mContext = context;
        this.setContentView(R.layout.dialog_edit_job_functions);
        mListView = (ListView) findViewById(R.id.jobfunction_listview);

        mOKButton = (Button) findViewById(R.id.btn_job_ok);
        mDeleteButton = (Button) findViewById(R.id.btn_job_delete);
        mEditButton = (Button) findViewById(R.id.btn_job_edit);
        dbHelper = new DatabaseHelper(mContext);

        mAdapter = new JobArrayAdapter(mContext, R.layout.job_list_item, dbHelper.getAllJobFunctions());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemSelectedListener(itemSelectedListener);
        mOKButton.setOnClickListener(mButtonClickListener);
        mDeleteButton.setOnClickListener(mButtonClickListener);
        mEditButton.setOnClickListener(mButtonClickListener);
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "position = " + position);
            mListView.setItemChecked(position, true);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "on click");
            int _id = v.getId();
            switch (_id) {
                case R.id.btn_job_delete:
                    handleDelete();
                    break;
                case R.id.btn_job_edit:
                    handleEdit();
                    break;
                case R.id.btn_job_ok:
                    handleOK();
                    break;
                default:
                    break;
            }
        }
    };

    private void handleDelete() {
        if (inDeleteMode) {
            mEditButton.setVisibility(View.VISIBLE);
            inDeleteMode = false;
            mDeleteButton.setText(mContext.getString(R.string.add));
        } else {
            inDeleteMode = true;
            mEditButton.setVisibility(View.INVISIBLE);
            mDeleteButton.setText(mContext.getString(R.string.cancel));
        }
        mAdapter.setDeleteMode(inDeleteMode);
    }
    private void deleteJobs() {
        List<String> mArray = mAdapter.getSeleted();
        for (int i = 0; i < mArray.size(); i++) {
            mAdapter.remove(mArray.get(i));
        }
        dbHelper.removeMultiJobFunction(mArray);
        inDeleteMode = false;
        mAdapter.setDeleteMode(inDeleteMode);
        mAdapter.clearEnable();
        mAdapter.notifyDataSetChanged();
    }


    private void handleEdit() {
        if (DEBUG) Log.d(TAG, "Edit click");
        LayoutInflater inflater = getLayoutInflater();
        final View addJobFunctionView = inflater.inflate(R.layout.dialog_add_item, null);
        final android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
        alertBuilder.setView(addJobFunctionView);
        alertBuilder.setCancelable(false);
        final android.support.v7.app.AlertDialog alertDialog = alertBuilder.create();

        ((TextView)addJobFunctionView.findViewById(R.id.title)).setText(mContext.getResources().getString(R.string.add_job_function));
        ((Button)addJobFunctionView.findViewById(R.id.canel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        ((Button)addJobFunctionView.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                String newJobFunction =
                        ((EditText)addJobFunctionView.findViewById(R.id.edittext)).getText().toString();

                if (dbHelper.isJobFunctionExisted(newJobFunction)) {
                    TextView warningMessageTextView = (TextView)addJobFunctionView.findViewById(R.id.error_message);

                    warningMessageTextView.setText(mContext.getResources().getString(R.string.duplicate_job_function));
                    warningMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    dbHelper.addJobFunction(newJobFunction);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    private void handleOK () {
        if (mAdapter == null) return;
        if (DEBUG) Log.d(TAG, "ok click");
        if (inDeleteMode) {
            List<String> mArray = mAdapter.getSeleted();
            if (mArray != null && mArray.size() > 0) {
                LayoutInflater inflater = getLayoutInflater();
                final View addJobFunctionView = inflater.inflate(R.layout.dialog_delete_alarm, null);
                final android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                alertBuilder.setView(addJobFunctionView);
                alertBuilder.setCancelable(false);
                final android.support.v7.app.AlertDialog alertDialog = alertBuilder.create();

                ((TextView)addJobFunctionView.findViewById(R.id.title)).setText(mContext.getResources().getString(R.string.delete_job_function));
                ((TextView)addJobFunctionView.findViewById(R.id.warnning_message)).setText(mContext.getResources().getString(R.string.delete_jobs_function_mesage));
                ((Button)addJobFunctionView.findViewById(R.id.canel_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                ((Button)addJobFunctionView.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteJobs();
                    }
                });
                alertDialog.show();
            }
        } else {
            this.dismiss();
        }
    }
}
