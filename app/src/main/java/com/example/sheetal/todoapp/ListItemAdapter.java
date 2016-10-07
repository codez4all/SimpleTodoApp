package com.example.sheetal.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sheetal.
 */
public class ListItemAdapter extends ArrayAdapter<ListItem> {



    public static  class ViewHolder
    {
        TextView tvListItem;
        TextView tvDueDate;
    }

    public ListItemAdapter(Context context, ArrayList<ListItem> listArray) {
        super(context, 0, listArray);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItem item = getItem(position);
        ViewHolder viewholder;

        if (convertView == null)
        {
            viewholder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list,parent,false);

            viewholder.tvListItem = (TextView) convertView.findViewById(R.id.tvListItem);
            viewholder.tvDueDate = (TextView)  convertView.findViewById(R.id.tvDueDate);

            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.tvListItem.setText(item.text);

        if(item.dueDate !=null)
            viewholder.tvDueDate.setText(item.dueDate.toString());

        return  convertView;
    }
}
