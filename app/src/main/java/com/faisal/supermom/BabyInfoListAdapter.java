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
 * Created by faisal on 8/21/2017.
 */

public class BabyInfoListAdapter extends ArrayAdapter<adminBabyInfo> {

    private Activity context;
    private List<adminBabyInfo> babyInfoList;//the list will contain all the artist and I named the list as artistList

    public BabyInfoListAdapter(Activity context,List<adminBabyInfo> babyInfoList){
        super(context,R.layout.list_layout_for_baby_info,babyInfoList);
        this.context = context;
        this.babyInfoList = babyInfoList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItems = inflater.inflate(R.layout.list_layout_for_baby_info,null,true);

        //now as we have 2 text view to show, so we will create 2 text view now

        TextView babyInfoHeadlineTextView = (TextView)listViewItems.findViewById(R.id.babyInfoHeadlineID);
        TextView babyInfoTextView = (TextView)listViewItems.findViewById(R.id.babyInfoID);

        adminBabyInfo babyInfoObj = babyInfoList.get(position);

        babyInfoHeadlineTextView.setText(babyInfoObj.getBabyInfoHeadline());
        babyInfoTextView.setText(babyInfoObj.getBabyInfo());


        return  listViewItems;


    }

}
