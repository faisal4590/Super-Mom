package com.faisal.supermom;

import android.app.Activity;
import android.support.annotation.LayoutRes;
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

import static com.faisal.supermom.R.id.showNewsfeedImageID;
import static com.faisal.supermom.R.id.showNewsfeedImageNameID;

/**
 * Created by faisal on 9/8/2017.
 */

public class AdapterForNewsfeedImageListView extends ArrayAdapter<ForNewsfeed_UploadImageNameAndUriClass> {

    private Activity context;
    private int resource;
    private List<ForNewsfeed_UploadImageNameAndUriClass> listImage;




    public AdapterForNewsfeedImageListView(@NonNull Activity context,
                                           @LayoutRes int resource,
                                           @NonNull List<ForNewsfeed_UploadImageNameAndUriClass> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource,null);

        TextView imageName = (TextView) v.findViewById(showNewsfeedImageNameID);
        ImageView img = (ImageView) v.findViewById(showNewsfeedImageID);

        imageName.setText(listImage.get(position).getImageName());

        Glide.with(context).load(listImage.get(position).getImageUrl()).into(img);

        return v;



    }
}
