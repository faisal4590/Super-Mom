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
 * Created by faisal on 10/15/2017.
 */

public class VaccineInfoListAdapter extends ArrayAdapter<AdminAddVaccineInfoClass> {

    private Activity context;
    private List<AdminAddVaccineInfoClass> vaccineInfoListNew;//the list will contain all the artist and I named the list as artistList


    public VaccineInfoListAdapter(Activity context,List<AdminAddVaccineInfoClass> vaccineInfoListNew){
        super(context,R.layout.list_layout_for_vaccine_info,vaccineInfoListNew);
        this.context = context;
        this.vaccineInfoListNew = vaccineInfoListNew;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItems = inflater.inflate(R.layout.list_layout_for_vaccine_info,null,true);

        TextView  vaccineLocationTextView = (TextView)listViewItems.findViewById (R.id.vaccineLocationID);
        TextView vaccineDetailsTextView = (TextView)listViewItems.findViewById (R.id.vaccineDetailsInfoID);
        TextView vaccineAvailableForTextView = (TextView)listViewItems.findViewById (R.id.vaccineAvailableForID);

        AdminAddVaccineInfoClass vaccineInfoObj5 = vaccineInfoListNew.get(position);

        vaccineLocationTextView.setText(vaccineInfoObj5.getVaacineLocation());
        vaccineDetailsTextView.setText(vaccineInfoObj5.getVaccineDetails());
        vaccineAvailableForTextView.setText(vaccineInfoObj5.getVaccineAvailableFor());
        return listViewItems;


    }


}
