package com.alfa.alfanieuws.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfa.alfanieuws.R;

import java.util.ArrayList;
import java.util.HashMap;

//TODO CODE COMMENTS
public class CommentAdapter extends BaseAdapter {
    // Initializing all the elements
    private Context context;
    private ArrayList<HashMap<String, String>> dataList;

    // Constructor sets all the elements
    public CommentAdapter(Context c, ArrayList<HashMap<String, String>> d) {
        context = c;
        dataList = d;
    }

    // Required function, we don't need it for now tho..
    public int getCount() {
        return dataList.size();
    }

    // Required function, we don't need it for now tho..
    public Object getItem(int position) {
        return position;
    }

    // Required function, we don't need it for now tho..
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder holder = null;
        if (convertView == null) {
            holder = new CommentViewHolder();
            // inflate layout and creating a TextView with the commentViewHolder
            convertView = LayoutInflater.from(context).inflate(R.layout.list_comment, null);
            holder.rowCommentName = convertView.findViewById(R.id.row_comment_name);
            holder.rowCommentText = convertView.findViewById(R.id.row_comment_text);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }

        // setting the id inside the holder for processing the data later; doing it for the name and the comment
        final HashMap<String, String> singleTask = dataList.get(position);
        holder.rowCommentName.setId(position);
        holder.rowCommentText.setId(position);

        try {
            // Populating the comments list with the comments from the dataList setting the text in the holders
            holder.rowCommentName.setText(singleTask.get("name"));
            holder.rowCommentText.setText(singleTask.get("text"));
        } catch (Exception e) {
        }

        return convertView;
    }
}

class CommentViewHolder {
    TextView rowCommentName, rowCommentText;
}