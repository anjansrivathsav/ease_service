package com.ease_service.shobhit.easeservice;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MachineList extends ArrayAdapter<MachineInfo> {
    private Activity context;
    private List<MachineInfo> machineInfoList;
    public MachineList(Activity context,List<MachineInfo> machineInfoList){
        super(context,R.layout.content_list_view,machineInfoList);
        this.context = context;
        this.machineInfoList = machineInfoList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.content_list_view,null,true);
        TextView textViewName  = (TextView) listViewItem.findViewById(R.id.machine_name);
        TextView textViewDesp  = (TextView) listViewItem.findViewById(R.id.desp);
        TextView textViewATime  = (TextView) listViewItem.findViewById(R.id.ac_time);
        TextView textViewETime  = (TextView) listViewItem.findViewById(R.id.end_time);

        MachineInfo machineInfo = machineInfoList.get(position);

        textViewName.setText(machineInfo.machine_number);
        textViewDesp.setText(machineInfo.description);
        textViewATime.setText(machineInfo.accept_time);
        textViewETime.setText(machineInfo.end_time);
        return listViewItem;
    }

}
