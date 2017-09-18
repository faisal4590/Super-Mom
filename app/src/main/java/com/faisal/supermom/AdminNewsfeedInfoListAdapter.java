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
 * Created by faisal on 9/18/2017.
 */

public class AdminNewsfeedInfoListAdapter extends ArrayAdapter<NewNewsfeedClass> {

    private Activity context;
    private List<NewNewsfeedClass> newsfeedInfoListNew;//the list will contain all the artist and I named the list as artistList


    public AdminNewsfeedInfoListAdapter(Activity context,List<NewNewsfeedClass> newsfeedListNew){
        super(context,R.layout.list_layout_for_baby_info,newsfeedListNew);
        this.context = context;
        this.newsfeedInfoListNew = newsfeedListNew;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItems = inflater.inflate(R.layout.list_layout_for_baby_info,null,true);


        TextView newsfeedHeadlineTextView = (TextView)listViewItems.findViewById(R.id.babyInfoHeadlineID);
        TextView newsfeedInfoTextView = (TextView)listViewItems.findViewById(R.id.babyInfoID);
        ImageView newsfeedImageImageView = (ImageView) listViewItems.findViewById(R.id.showNewsfeedImageID);
        TextView newsfeedImageNameTextView = (TextView)listViewItems.findViewById(R.id.showNewsfeedImageNameID);

        NewNewsfeedClass newsfeedInfoObj5 = newsfeedInfoListNew.get(position);

        newsfeedHeadlineTextView.setText(newsfeedInfoObj5.getNewBabyInfoHeadline());
        newsfeedInfoTextView.setText(newsfeedInfoObj5.getNewBabyInfo());
        newsfeedImageNameTextView.setText(newsfeedInfoObj5.getNewNewsfeedImageName());

        Glide.with(context).load(newsfeedInfoListNew.get(position).getNewNewsfeedImageUrl()).into(newsfeedImageImageView);

        return listViewItems;


    }

}
