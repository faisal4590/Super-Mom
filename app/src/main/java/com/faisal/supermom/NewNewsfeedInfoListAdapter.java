package com.faisal.supermom;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by faisal on 9/16/2017.
 */

public class NewNewsfeedInfoListAdapter extends ArrayAdapter<NewNewsfeedClass> {

    private Activity context;
    private List<NewNewsfeedClass> babyInfoList;//the list will contain all the artist and I named the list as artistList

    private int resource;

    public NewNewsfeedInfoListAdapter(@NonNull Activity context,

                                           @NonNull List<NewNewsfeedClass> objects) {
        super(context, R.layout.list_layout_for_baby_info, objects);

        this.context = context;
        babyInfoList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItems = inflater.inflate(R.layout.list_layout_for_baby_info,null,true);

        //now as we have 2 text view to show, so we will create 2 text view now

        TextView babyInfoHeadlineTextView = (TextView)listViewItems.findViewById(R.id.babyInfoHeadlineID);
        TextView babyInfoTextView = (TextView)listViewItems.findViewById(R.id.babyInfoID);
        TextView newsFeedImageNameTextView = (TextView)listViewItems.findViewById(R.id.showNewsfeedImageNameID);
        ImageView newsFeedImageImageView = (ImageView) listViewItems.findViewById(R.id.showNewsfeedImageID);


        NewNewsfeedClass babyInfoObj = babyInfoList.get(position);

        babyInfoHeadlineTextView.setText(babyInfoObj.getNewBabyInfoHeadline());
        babyInfoTextView.setText(babyInfoObj.getNewBabyInfo());
        newsFeedImageNameTextView.setText(babyInfoObj.getNewNewsfeedImageName());

        Glide.with(context).load(babyInfoList.get(position).getNewNewsfeedImageUrl()).into(newsFeedImageImageView);


        return  listViewItems;


    }


}
