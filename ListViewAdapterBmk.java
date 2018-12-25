package com.example.princesaoud.dictionnaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewAdapterBmk extends BaseAdapter {

    ArrayList<String> source;
    private ListItemListener listener;
    private ListItemListener listenerBtnDelete;
    private Context mContext;

    public ListViewAdapterBmk(Context mContext, ArrayList<String> source){
        this.mContext = mContext;
        this.source = source;
    }
    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public Object getItem(int position) {
        return source.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bookmarlistview, parent,false);
            holder.textView = convertView.findViewById(R.id.tvWord);
            holder.imageView = convertView.findViewById(R.id.btDelete);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
            holder.textView.setText(source.get(position));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        listener.onItemClick(position);
                    }
                }
            });

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerBtnDelete != null)
                        listenerBtnDelete.onItemClick(position);
                }
            });

        return convertView;
    }

    public void removeItem(int position){
        source.remove(position);
    }

    public void setOnItemClick(ListItemListener listener){
        this.listener = listener;
    }
    public void setOnItemButtonClick(ListItemListener listenerBtnDelete){
        this.listenerBtnDelete = listenerBtnDelete;
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;

    }

}
