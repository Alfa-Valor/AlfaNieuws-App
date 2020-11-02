package com.alfa.alfanieuws.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alfa.alfanieuws.R;
import java.util.ArrayList;
import java.util.HashMap;

//TODO CODE COMMENTS
public class CommentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> dataList;

    public CommentAdapter(Context c, ListView listView, ArrayList<HashMap<String, String>> d) {
        context = c;
        dataList = d;
        listView = listView;
        System.out.println("DATALIST ONPOST 1" + dataList);
    }

    public int getCount() {
        return dataList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder holder = null;
        System.out.println("DATALIST ONPOST 2" + dataList);
        if (convertView == null) {
            holder = new CommentViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_comment, null);
            holder.rowCommentName = convertView.findViewById(R.id.row_comment_name);
            holder.rowCommentText = convertView.findViewById(R.id.row_comment_text);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }

        final HashMap<String, String> singleTask = dataList.get(position);
        System.out.println("POS" + position);
        holder.rowCommentName.setId(position);
        holder.rowCommentText.setId(position);

        try {
            System.out.println("DATALIST ONPOST 3" + dataList);
            holder.rowCommentName.setText(singleTask.get("name"));
            holder.rowCommentText.setText(singleTask.get("text"));
        } catch (Exception e) {
        }
        System.out.println("DATALIST " + dataList);

        return convertView;
    }
}
class CommentViewHolder {
    TextView rowCommentName, rowCommentText;
}