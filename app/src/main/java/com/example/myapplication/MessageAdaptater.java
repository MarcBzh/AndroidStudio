package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdaptater extends BaseAdapter {

    public MessageAdaptater(Activity context){
        this.context = context;
    }
    Activity context;
    List<Message> messages;


    @Override
    public int getCount() {
        if (this.messages == null) {
            return 0;
        }
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return this.messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (context.getLayoutInflater());
        convertView = inflater.inflate(R.layout.item, parent, false);
        TextView txt = convertView.findViewById(R.id.textView);
        txt.setText(getItem(position).msg);
        return convertView;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        this.notifyDataSetChanged();
    }
}
