package com.faisal.supermom;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by faisal on 8/22/2017.
 */

public class AdminDoctorInfoListAdapterNew extends ArrayAdapter<DoctorClassNew> {

    private Activity context;
    private List<DoctorClassNew> doctorInfoListNew;//the list will contain all the artist and I named the list as artistList


    public AdminDoctorInfoListAdapterNew(Activity context,List<DoctorClassNew> doctorInfoListNew){
        super(context,R.layout.list_layout_for_doctor_info_new,doctorInfoListNew);
        this.context = context;
        this.doctorInfoListNew = doctorInfoListNew;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItems = inflater.inflate(R.layout.list_layout_for_doctor_info_new,null,true);


        TextView doctorNameTextView = (TextView)listViewItems.findViewById(R.id.showDoctorNameAreaID);
        TextView doctorHostpitalTextView = (TextView)listViewItems.findViewById(R.id.showDoctorHospitalAreaID);
        TextView doctorAvailibilityTextView = (TextView)listViewItems.findViewById(R.id.showDoctorAvailibilityAreaID);
        TextView doctorSpecializedTextView = (TextView)listViewItems.findViewById(R.id.showDoctorSpecializedAreaID);
        TextView doctorContactTextView = (TextView)listViewItems.findViewById(R.id.showDoctorContactAreaID);

        DoctorClassNew doctorInfoObj5 = doctorInfoListNew.get(position);

        doctorNameTextView.setText(doctorInfoObj5.getDoctorNameVar());
        doctorHostpitalTextView.setText(doctorInfoObj5.getDoctorHospitalVar());
        doctorSpecializedTextView.setText(doctorInfoObj5.getDoctorSpecialityVar());
        doctorAvailibilityTextView.setText(doctorInfoObj5.getDoctorAvailibilityVar());
        doctorContactTextView.setText(doctorInfoObj5.getDoctorContactNoVar());

        return listViewItems;


    }
}
