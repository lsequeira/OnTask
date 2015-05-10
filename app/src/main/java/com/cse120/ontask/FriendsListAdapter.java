package com.cse120.ontask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpConnection;

import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

class FriendsListAdapter extends ArrayAdapter<String> {

    TaskManagerApplication myApp;

    public FriendsListAdapter(Context context, List<String> friends) {
        super(context, R.layout.friends_list_view, friends);
        TaskManagerApplication app = (TaskManagerApplication) context.getApplicationContext();
        myApp = app;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.friends_list_view, parent, false);



        String friend = getItem(position);
        TextView friendName = (TextView) customView.findViewById(R.id.friendName);
        ImageView friendImage = (ImageView) customView.findViewById(R.id.friendImage);

        friendName.setText(friend);

        return customView;
    }


}
