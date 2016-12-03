package com.cei.sdbg.resume;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alumincan on 2016/11/15.
 */

public class JobArrayAdapter extends ArrayAdapter {
    private final String TAG = "JobArrayAdapter";
    private Context mContext;
    private int mResourceID;
    private DatabaseHelper dbHelper;
    private LayoutInflater mInflater;
    private List<String> mJobList;
    private int mPosition = NOT_SELECTED;
    private static final int NOT_SELECTED = -1;
    private ArrayList<String> mEnableList;
    private boolean inDeleteMode = false;

    public JobArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourceID = resource;
        mInflater = ( LayoutInflater ) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mJobList = objects;
        mEnableList = new ArrayList<String>();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View row = convertView;
        if (row == null) {
            row = mInflater.inflate(mResourceID, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) row.findViewById(R.id.address_view);
            holder.checkBox = (CheckBox) row.findViewById(R.id.radioButton);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inDeleteMode) {
                    Log.d(TAG, "position = " + position);
                    if (mEnableList.contains(mJobList.get(position))) {
                        //((TextView)(v.findViewById(R.id.address_view))).setBackgroundColor(mContext.getResources().getColor(R.color.colorControlGreen));
                        ((CheckBox) (v.findViewById(R.id.radioButton))).setChecked(false);
                        mEnableList.remove(mJobList.get(position));
                    } else {
                        //v.findViewById(R.id.address_view).setBackgroundColor(mContext.getResources().getColor(internal.R.));
                        ((CheckBox) (v.findViewById(R.id.radioButton))).setChecked(true);
                        mEnableList.add(mJobList.get(position));
                    }
                    notifyDataSetChanged();
                }
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inDeleteMode) {
                    CheckBox r = (CheckBox) v;
                    if (mEnableList.contains(mJobList.get(position))) {
                        r.setChecked(false);
                        mEnableList.remove(mJobList.get(position));
                    } else {
                        r.setChecked(true);
                        mEnableList.add(mJobList.get(position));
                    }
                }
            }
        });
        if (inDeleteMode) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(mEnableList.contains(mJobList.get(position)));
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        holder.textView.setText(mJobList.get(position));
        return row;
    }

    public List<String> getSeleted() {
        if (mEnableList != null)
            return mEnableList;
        return null;
    }

    public void setDeleteMode(boolean mode) {
        inDeleteMode = mode;
        this.notifyDataSetChanged();
    }

    public void clearEnable() {
        mEnableList.clear();
    }

    public class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }
}
