package com.example.uzair.expertdentalcare;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Uzair on 1/8/2017.
 */

public class ViewAppointmentListAdapter extends ArrayAdapter<AppointmentDatatype> {

    Context context;
    int layoutResourceId;
    List<AppointmentDatatype> data = null;

    public ViewAppointmentListAdapter(Context context, int layoutResourceId, List<AppointmentDatatype> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AppointmentHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AppointmentHolder();

            holder.dr_name = (TextView) row.findViewById(R.id.dr_name);
            holder.date_time = (TextView) row.findViewById(R.id.date_time);
            row.setTag(holder);
        } else {
            holder = (AppointmentHolder) row.getTag();
        }

        holder.dr_name.setText(data.get(position).DentistName);
        holder.date_time.setText(data.get(position).DateTime);


        return row;
    }

    static class AppointmentHolder {

        TextView dr_name, date_time;
    }
}
